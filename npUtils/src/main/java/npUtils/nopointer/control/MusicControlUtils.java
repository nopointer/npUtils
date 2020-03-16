package npUtils.nopointer.control;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 音乐控制 工具类
 */
public class MusicControlUtils {

    /**
     * 获取音频管理器
     *
     * @param context
     * @return
     */
    private static AudioManager getAudioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * 判断是否在播放音乐
     *
     * @param context
     * @return
     */
    private static boolean isMusicPlaying(Context context) {
        AudioManager audioManager = getAudioManager(context);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        boolean isMusicActive = audioManager.isMusicActive();
        Log.e("fuck,isMusicActive", isMusicActive + "====>");
        return isMusicActive;
    }


    /**
     * 上一首
     *
     * @param context
     */
    public static void previous(Context context) {
        MusicPlayerInfoBean musicPlayerInfoBean = MusicPlayerInfoUtils.getPlayer(context);
        if (musicPlayerInfoBean == null) {
            Log.e("fuck,", "本地没有安装播放器");
            return;
        }
        if (!isMusicPlaying(context)) {
            return;
        }
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS, musicPlayerInfoBean.getComponentName());
    }

    /**
     * 暂停/播放
     *
     * @param context
     */
    public static void startPause(Context context) {
        MusicPlayerInfoBean musicPlayerInfoBean = MusicPlayerInfoUtils.getPlayer(context);
        if (musicPlayerInfoBean == null) {
            Log.e("fuck,", "本地没有安装播放器");
            return;
        }
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, musicPlayerInfoBean.getComponentName());
    }


    /**
     * 下一首
     *
     * @param context
     */
    public static void nextSong(Context context) {
        MusicPlayerInfoBean musicPlayerInfoBean = MusicPlayerInfoUtils.getPlayer(context);
        if (musicPlayerInfoBean == null) {
            Log.e("fuck,", "本地没有安装播放器");
            return;
        }
        if (!isMusicPlaying(context)) {
            return;
        }
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_NEXT, musicPlayerInfoBean.getComponentName());
    }


    /**
     * 封装发送广播
     *
     * @param context
     * @param keyCode
     */
    @SuppressLint("MissingPermission")
    private static void sendOrderedBroadcast(Context context, int keyCode, ComponentName componentName) {
        send(context, keyCode, componentName);
    }

    private static void send(Context context, int keyCode, ComponentName componentName) {
        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        downIntent.setComponent(componentName);
        KeyEvent downEvent = new KeyEvent(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), KeyEvent.ACTION_DOWN, keyCode, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        downIntent.setFlags(0x10000010);

        context.sendOrderedBroadcast(downIntent, null);

        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        upIntent.setComponent(componentName);
        KeyEvent upEvent = new KeyEvent(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), KeyEvent.ACTION_UP, keyCode, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        upIntent.setFlags(0x10000010);
        context.sendOrderedBroadcast(upIntent, null);
    }

}
