package npUtils.nopointer.control;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicPlayerInfoUtils {


    private static HashMap<String, MusicPlayerInfoBean> musicPlayerInfoBeanHashMap = new HashMap<>();

    public MusicPlayerInfoUtils() {
        //网易云的播放器
        MusicPlayerInfoBean playerInfoBean = new MusicPlayerInfoBean("com.netease.cloudmusic", "com.netease.cloudmusic.receiver.MediaButtonEventReceiver");
        musicPlayerInfoBeanHashMap.put(playerInfoBean.getPackName(), playerInfoBean);

        //qq音乐播放器
        playerInfoBean = new MusicPlayerInfoBean("com.tencent.qqmusic", "android.support.v4.media.session.MediaButtonReceiver");
        musicPlayerInfoBeanHashMap.put(playerInfoBean.getPackName(), playerInfoBean);

        //小米音乐
        playerInfoBean = new MusicPlayerInfoBean("com.miui.player", "com.miui.player.receiver.MediaButtonIntentReceiver");
        musicPlayerInfoBeanHashMap.put(playerInfoBean.getPackName(), playerInfoBean);

    }

    public static MusicPlayerInfoBean getPlayer(Context context) {
        MusicPlayerInfoBean musicPlayerInfoBean = getCurrentRunningMusicPlayInfo(context);
        if (musicPlayerInfoBean == null) {
            musicPlayerInfoBean = getLocalFirstMusicPlayInfo(context);
        }
        return musicPlayerInfoBean;
    }

    /**
     * 获取正在播放的音乐播放器,如果有多个的话，就取第一个，如果一个都没有的话，取本地排第一的，如果本地没有，就返回null
     *
     * @param context
     * @return
     */
    public static MusicPlayerInfoBean getCurrentRunningMusicPlayInfo(Context context) {
        MusicPlayerInfoBean musicPlayerInfoBean = null;

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(10000);

        //现在运行的包名
        List<String> runningPackNameList = new ArrayList<>();
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
            runningPackNameList.add(runningServiceInfo.process);
        }
        String playerAppPackName = null;
        //本地安装的包名
        List<String> localPackNameList = getLocalInstallPlayerPackName(context);
        for (String localPackName : localPackNameList) {
            if (runningPackNameList.contains(localPackName)) {
                playerAppPackName = localPackName;
                break;
            }
        }
        if (TextUtils.isEmpty(playerAppPackName)) {
            musicPlayerInfoBean = mappingMusicPlayerInfo(playerAppPackName);
        }
        return musicPlayerInfoBean;
    }

    /**
     * 获取本地安装列表里面的第一个音乐播放器
     *
     * @return
     */
    public static MusicPlayerInfoBean getLocalFirstMusicPlayInfo(Context context) {
        List<String> packNameList = getLocalInstallPlayerPackName(context);
        MusicPlayerInfoBean musicPlayerInfoBean = null;
        if (packNameList != null && packNameList.size() > 0) {
            String packName = packNameList.get(0);
            musicPlayerInfoBean = mappingMusicPlayerInfo(packName);
        }
        return musicPlayerInfoBean;
    }


    /**
     * 获取本地安装的播放器的包名
     *
     * @param context
     * @return
     */
    public static List<String> getLocalInstallPlayerPackName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        List<ResolveInfo> resolveInfoList = packageManager.queryBroadcastReceivers(intent, PackageManager.MATCH_ALL);


//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//        intent.setDataAndType(Uri.fromFile(new File("")), "audio/*");// type:改成"video/*"表示获取视频的
//        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        List<String> packNameList = new ArrayList<>();
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            for (ResolveInfo resolveInfo : resolveInfoList) {
//                Log.e("fuck,activityInfo",resolveInfo.activityInfo.packageName);
                packNameList.add(resolveInfo.activityInfo.packageName);
            }
        }
        packNameList.remove("com.alibaba.android.rimet");
        packNameList.remove("com.chaozh.iReaderFree");
        packNameList.remove("com.xiaomi.smarthome");
        packNameList.remove("com.miui.cit");
        packNameList.remove("cn.wps.moffice_eng");
        packNameList.remove("com.duokan.reader");
        packNameList.remove("com.estrongs.android.pop");
        packNameList.remove("com.mxtech.videoplayer.ad");
        packNameList.remove("com.xiaomi.smarthome");
        return packNameList;
    }


    /**
     * 通过包名映射的第三方音乐播放器
     *
     * @param appPackName
     * @return
     */
    public static MusicPlayerInfoBean mappingMusicPlayerInfo(String appPackName) {
        return musicPlayerInfoBeanHashMap.get(appPackName);
    }


}
