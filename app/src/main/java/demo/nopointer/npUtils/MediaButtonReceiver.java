package demo.nopointer.npUtils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.RestrictTo;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;

import java.util.List;


public class MediaButtonReceiver extends BroadcastReceiver {
    private static final String TAG = "MediaButtonReceiver";

    public MediaButtonReceiver() {
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null && "android.intent.action.MEDIA_BUTTON".equals(intent.getAction()) && intent.hasExtra("android.intent.extra.KEY_EVENT")) {
            ComponentName mediaButtonServiceComponentName = getBroadcastComponentByAction(context, "android.intent.action.MEDIA_BUTTON");
            if (mediaButtonServiceComponentName != null) {
                intent.setComponent(mediaButtonServiceComponentName);
                startForegroundService(context, intent);
            } else {
                ComponentName mediaBrowserServiceComponentName = getServiceComponentByAction(context, "android.media.browse.MediaBrowserService");
                if (mediaBrowserServiceComponentName != null) {
                    PendingResult pendingResult = this.goAsync();
                    Context applicationContext = context.getApplicationContext();
                    MediaButtonConnectionCallback connectionCallback = new MediaButtonConnectionCallback(applicationContext, intent, pendingResult);
                    MediaBrowserCompat mediaBrowser = new MediaBrowserCompat(applicationContext, mediaBrowserServiceComponentName, connectionCallback, (Bundle) null);
                    connectionCallback.setMediaBrowser(mediaBrowser);
                    mediaBrowser.connect();
                } else {
                    throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
                }
            }
        } else {
            Log.d("MediaButtonReceiver", "Ignore unsupported intent: " + intent);
        }
    }

    public static KeyEvent handleIntent(MediaSessionCompat mediaSessionCompat, Intent intent) {
        if (mediaSessionCompat != null && intent != null && "android.intent.action.MEDIA_BUTTON".equals(intent.getAction()) && intent.hasExtra("android.intent.extra.KEY_EVENT")) {
            KeyEvent ke = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
            MediaControllerCompat mediaController = mediaSessionCompat.getController();
            mediaController.dispatchMediaButtonEvent(ke);
            return ke;
        } else {
            return null;
        }
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context context, long action) {
        ComponentName mbrComponent = getMediaButtonReceiverComponent(context);
        if (mbrComponent == null) {
            Log.w("MediaButtonReceiver", "A unique media button receiver could not be found in the given context, so couldn't build a pending intent.");
            return null;
        } else {
            return buildMediaButtonPendingIntent(context, mbrComponent, action);
        }
    }

    public static PendingIntent buildMediaButtonPendingIntent(Context context, ComponentName mbrComponent, long action) {
        if (mbrComponent == null) {
            Log.w("MediaButtonReceiver", "The component name of media button receiver should be provided.");
            return null;
        } else {
            int keyCode = PlaybackStateCompat.toKeyCode(action);
            if (keyCode == 0) {
                Log.w("MediaButtonReceiver", "Cannot build a media button pending intent with the given action: " + action);
                return null;
            } else {
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                intent.setComponent(mbrComponent);
                intent.putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(0, keyCode));
                return PendingIntent.getBroadcast(context, keyCode, intent, 0);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static ComponentName getMediaButtonReceiverComponent(Context context) {
        Intent queryIntent = new Intent("android.intent.action.MEDIA_BUTTON");
//        queryIntent.setPackage(context.getPackageName());
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(queryIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfos.size() == 1) {
            ResolveInfo resolveInfo = (ResolveInfo) resolveInfos.get(0);
            return new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        } else {
            if (resolveInfos.size() > 1) {
                for (ResolveInfo resolveInfo : resolveInfos) {
                    Log.e("fuck,reInfo", resolveInfo.activityInfo.packageName + "///" + resolveInfo.activityInfo.name);
                }
//                Log.w("MediaButtonReceiver", "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null.");
            }

            return null;
        }
    }

    private static void startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }

    }

    private static ComponentName getServiceComponentByAction(Context context, String action) {
        PackageManager pm = context.getPackageManager();
        Intent queryIntent = new Intent(action);
//        queryIntent.setPackage(context.getPackageName());
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(queryIntent, 0);
        if (resolveInfos.size() == 1) {
            ResolveInfo resolveInfo = (ResolveInfo) resolveInfos.get(0);
            return new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        } else if (resolveInfos.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Expected 1 service that handles " + action + ", found " + resolveInfos.size());
        }
    }


    private static ComponentName getBroadcastComponentByAction(Context context, String action) {
        PackageManager pm = context.getPackageManager();
        Intent queryIntent = new Intent(action);
        List<ResolveInfo> resolveInfos = pm.queryBroadcastReceivers(queryIntent, 0);
        Log.e("fuck", (resolveInfos == null) + "");
        if (resolveInfos.size() == 1) {
            ResolveInfo resolveInfo = (ResolveInfo) resolveInfos.get(0);
            return new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
        } else if (resolveInfos.isEmpty()) {
            return null;
        } else {
            throw new IllegalStateException("Expected 1 service that handles " + action + ", found " + resolveInfos.size());
        }
    }

    private static class MediaButtonConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
        private final Context mContext;
        private final Intent mIntent;
        private final PendingResult mPendingResult;
        private MediaBrowserCompat mMediaBrowser;

        MediaButtonConnectionCallback(Context context, Intent intent, PendingResult pendingResult) {
            this.mContext = context;
            this.mIntent = intent;
            this.mPendingResult = pendingResult;
        }

        void setMediaBrowser(MediaBrowserCompat mediaBrowser) {
            this.mMediaBrowser = mediaBrowser;
        }

        public void onConnected() {
            try {
                MediaControllerCompat mediaController = new MediaControllerCompat(this.mContext, this.mMediaBrowser.getSessionToken());
                KeyEvent ke = (KeyEvent) this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
                mediaController.dispatchMediaButtonEvent(ke);
            } catch (RemoteException var3) {
                Log.e("MediaButtonReceiver", "Failed to create a media controller", var3);
            }

            this.finish();
        }

        public void onConnectionSuspended() {
            this.finish();
        }

        public void onConnectionFailed() {
            this.finish();
        }

        private void finish() {
            this.mMediaBrowser.disconnect();
            this.mPendingResult.finish();
        }
    }
}