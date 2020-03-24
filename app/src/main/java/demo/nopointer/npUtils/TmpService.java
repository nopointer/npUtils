package demo.nopointer.npUtils;

import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.service.media.MediaBrowserService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.List;

import npUtils.nopointer.control.MusicControlUtils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TmpService extends MediaBrowserService {

    private Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowser.MediaItem>> result) {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MusicControlUtils.startPause(TmpService.this);
            }
        }, 3000);

    }
}
