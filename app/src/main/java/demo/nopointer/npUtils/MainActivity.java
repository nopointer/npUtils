package demo.nopointer.npUtils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.RemoteController;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.View;

import npUtils.nopointer.control.media.MediaControlUtils;
import npUtils.nopointer.control.phone.PhoneUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //上一首
        findViewById(R.id.control_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControlUtils.previous(MainActivity.this);
            }
        });

        //暂停/播放
        findViewById(R.id.control_start_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControlUtils.startPause(MainActivity.this);
            }
        });

        //下一首
        findViewById(R.id.control_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaControlUtils.nextSong(MainActivity.this);
            }
        });

        findViewById(R.id.answer_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.answerCall(MainActivity.this);
            }
        });


        findViewById(R.id.end_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneUtils.endCall(MainActivity.this);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},100);
        }
    }

}


