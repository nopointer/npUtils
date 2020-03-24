package demo.nopointer.npUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RemoteController;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;

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

//        fuck();
    }

    public MediaSessionCompat mMediaSession;
    private Handler sHandler;


    private void fuck() {
//        MediaControllerCompat mediaControllerCompat = MediaControllerCompat.
//        Log.e("fuck,mediaControllerCompat==null", (mediaControllerCompat == null) + "");
//
        mMediaSession = new MediaSessionCompat(this, "MusicService");
        sHandler = new Handler(Looper.getMainLooper());
        mMediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public boolean onMediaButtonEvent(Intent intent) {
                Log.e("fuck,", "onMediaButtonEvent");
                android.support.v4.media.session.MediaButtonReceiver mMediaButtonReceiver = new android.support.v4.media.session.MediaButtonReceiver();
//                mMediaButtonReceiver.onReceive(MainActivity.this, intent);
                return true;
            }
        }, sHandler);
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        Context context = getApplicationContext();
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 99 /*request code*/,
//                intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mMediaSession.setSessionActivity(pi);
//        try {
//            pi.send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//        }
        if (!mMediaSession.isActive()) {
            mMediaSession.setActive(true);
        }

        RemoteController remoteController =new RemoteController(this, new RemoteController.OnClientUpdateListener() {
            @Override
            public void onClientChange(boolean clearing) {

            }

            @Override
            public void onClientPlaybackStateUpdate(int state) {

            }

            @Override
            public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {

            }

            @Override
            public void onClientTransportControlUpdate(int transportControlFlags) {

            }

            @Override
            public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {

            }
        });
    }
}


