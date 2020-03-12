package npUtils.nopointer.control;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
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
        if (!isMusicPlaying(context)) {
            return;
        }
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
    }

    /**
     * 暂停/播放
     *
     * @param context
     */
    public static void startPause(Context context) {
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
    }

    /**
     * 下一首
     *
     * @param context
     */
    public static void nextSong(Context context) {
        if (!isMusicPlaying(context)) {
            return;
        }
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_NEXT);
    }


    /**
     * 封装发送广播
     *
     * @param context
     * @param keyCode
     */
    private static void sendOrderedBroadcast(Context context, int keyCode) {
        long eventtime = SystemClock.uptimeMillis();

        Intent downIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent downEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_DOWN, keyCode, 0);
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            downIntent.addFlags(0x01000000);
        }
        context.sendOrderedBroadcast(downIntent, null);

        Intent upIntent = new Intent(Intent.ACTION_MEDIA_BUTTON, null);
        KeyEvent upEvent = new KeyEvent(eventtime, eventtime, KeyEvent.ACTION_UP, keyCode, 0);
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            upIntent.addFlags(0x01000000);
        }
        context.sendOrderedBroadcast(upIntent, null);
    }

}
