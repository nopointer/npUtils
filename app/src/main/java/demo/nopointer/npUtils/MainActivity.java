package demo.nopointer.npUtils;

import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.media2.MediaSession2;
import android.support.media2.MediaSessionService2;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

import npUtils.nopointer.control.MusicControlUtils;

public class MainActivity extends Activity {


    private String TAG = "fuck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //上一首
        findViewById(R.id.control_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicControlUtils.previous(MainActivity.this);
            }
        });

        //暂停/播放
        findViewById(R.id.control_start_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicControlUtils.startPause(MainActivity.this);
            }
        });

        //下一首
        findViewById(R.id.control_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicControlUtils.nextSong(MainActivity.this);
            }
        });

        MediaSessionService2 mediaSessionService2 = new MediaSessionService2() {
            @NonNull
            @Override
            public MediaSession2 onCreateSession(String sessionId) {
                Log.e("fuck,onCreateSession", "onCreateSession");
                MediaSession2.Builder builder = new MediaSession2.Builder(MainActivity.this);
                return builder.build();
            }
        };
//        mediaSessionService2.sendBroadcast(keyEvent(this, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
//        mediaSessionService2.sendBroadcast(keyEvent(this, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        audioManager.setBluetoothA2dpOn(true);

        fuck();
    }


    private void fuck() {


//        PackageManager pm = getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
////        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
////        intent.setDataAndType(Uri.fromFile(new File("")), "audio/*");// type:改成"video/*"表示获取视频的
//        List<ResolveInfo> mResolveInfoList = pm.qu(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo ri : mResolveInfoList) {
//            Log.e(TAG, "应用名: " + ri.loadLabel(pm) + "///" + "包名: " + ri.activityInfo.packageName);
//        }
//
//        PackageManager packageManager = getPackageManager();

//        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
////        intent.addCategory(Intent.CATEGORY_DEFAULT);
////        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
//        List<PackageInfo> resolveInfoList = packageManager.getInstalledPackages(PackageManager.GET_RECEIVERS);
//        Log.e("fuck,resolveInfoList", resolveInfoList.size() + "");
//        for (PackageInfo resolveInfo : resolveInfoList) {
//            Log.e("fuck,resolveInfoList", resolveInfo.packageName + "///");
//        }

    }



    private static Intent keyEvent(Context context, int keyEvent, int keyCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_BUTTON);
//        intent.setPackage("com.netease.cloudmusic");
//        intent.setComponent(new ComponentName("com.netease.cloudmusic", "com.netease.cloudmusic.receiver.MediaButtonEventReceiver"));
        long time = SystemClock.uptimeMillis();
        KeyEvent event = new KeyEvent(time, time, keyEvent, keyCode, 0);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, event);
        intent.addFlags(0x01000010);
//        intent.addFlags(0x01000000|0x00400000);
        return intent;
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_ONE_SHOT);
//        try {
//            pendingIntent.send();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}


