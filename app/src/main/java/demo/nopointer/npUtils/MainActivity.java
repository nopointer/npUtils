package demo.nopointer.npUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.List;

import npUtils.nopointer.control.MusicControlUtils;

public class MainActivity extends Activity {

    AudioManager mAudioManager = null;

    private ComponentName mMediaButtonReceiverComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
//        startActivity(intent);

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




        PackageManager pm = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setDataAndType(Uri.fromFile(new File("")), "audio/*");// type:改成"video/*"表示获取视频的
        List<ResolveInfo> mResolveInfoList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//
        String TAG = "tag";
        for (ResolveInfo ri : mResolveInfoList) {

            Log.e(TAG, "Icon: " + ri.loadIcon(pm));
            Log.e(TAG, "应用名: " + ri.loadLabel(pm));
            Log.e(TAG, "包名: " + ri.activityInfo.packageName);

        }

//        Intent intent=new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(uri, MimeType);//媒体的Uri和MimeType
//        startActivity(intent);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("demo", "后台控制下一首");
//                MusicControlUtils.nextSong(MainActivity.this);
//            }
//        }, 5000);

        AudioManager mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//构造一个ComponentName，指向MediaoButtonReceiver类
//下面为了叙述方便，我直接使用ComponentName类来替代MediaoButtonReceiver类
        ComponentName mbCN = new ComponentName(getPackageName(), MediaButtonReceiver.class.getName());
//注册一个MedioButtonReceiver广播监听
        mAudioManager.registerMediaButtonEventReceiver(mbCN);
//取消注册的方法
//        mAudioManager.unregisterMediaButtonEventReceiver(mbCN);

//        start();
    }

    private void start(){
        ComponentName chatService =new ComponentName("com.tencent.qqmusic/android.support.v4.media.session.MediaButtonReceiver");

        Intent intent =new Intent();

        intent.setComponent(chatService );

        startService(intent);
    }


}


