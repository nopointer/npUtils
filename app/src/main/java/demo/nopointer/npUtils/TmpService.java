package demo.nopointer.npUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import npUtils.nopointer.control.MusicControlUtils;

public class TmpService extends Service {

    private Handler handler = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
