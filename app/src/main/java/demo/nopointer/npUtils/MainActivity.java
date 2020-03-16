package demo.nopointer.npUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;

import java.util.List;

import npUtils.nopointer.control.MusicControlUtils;
import npUtils.nopointer.control.MusicPlayerInfoBean;
import npUtils.nopointer.control.MusicPlayerInfoUtils;

public class MainActivity extends Activity {


    private String TAG = "fuck";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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

//                sendMusicKeyEvent(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
//                debug();
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

        MusicPlayerInfoUtils musicPlayerInfoUtils = new MusicPlayerInfoUtils();

        MusicPlayerInfoBean musicPlayerInfoBean = musicPlayerInfoUtils.getCurrentRunningMusicPlayInfo(this);
        if (musicPlayerInfoBean != null) {
            Log.e("fuck", musicPlayerInfoBean.getPackName());
        } else {
            Log.e("fuck", "空的");
        }
//        new MusicPlayerInfoUtils().getCurrentRunningMusicPlayInfo(this);

    }


    private void fuck() {
//        PackageManager pm = getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        intent.setDataAndType(Uri.fromFile(new File("")), "audio/*");// type:改成"video/*"表示获取视频的
//        List<ResolveInfo> mResolveInfoList = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo ri : mResolveInfoList) {
//            Log.e(TAG, "应用名: " + ri.loadLabel(pm) + "///" + "包名: " + ri.activityInfo.packageName);
//        }

        PackageManager packageManager = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        List<ResolveInfo> resolveInfoList = packageManager.queryBroadcastReceivers(intent, PackageManager.MATCH_ALL);
        Log.e("fuck,resolveInfoList", resolveInfoList.size() + "");
        for (ResolveInfo resolveInfo : resolveInfoList) {
            Log.e("fuck,resolveInfoList", resolveInfo.activityInfo.packageName + "///" + resolveInfo.activityInfo.maxRecents);
        }

    }


}


