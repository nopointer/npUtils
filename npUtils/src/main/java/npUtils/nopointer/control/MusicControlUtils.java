package npUtils.nopointer.control;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.SystemClock;
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
//        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        boolean isMusicActive = audioManager.isMusicActive();
        return isMusicActive;
    }


    /**
     * 上一首
     *
     * @param context
     */
    public static void previous(Context context) {
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
    }

    /**
     * 暂停/播放
     *
     * @param context
     */
    public static void startPause(Context context) {
        if (isMusicPlaying(context)) {
            sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PAUSE);
        } else {
            sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_PLAY);
        }
    }

    /**
     * 下一首
     *
     * @param context
     */
    public static void nextSong(Context context) {
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_NEXT);
    }

    /**
     * 封装发送广播
     *
     * @param context
     * @param keyCode
     */
    private static void sendOrderedBroadcast(Context context, int keyCode) {
        keyEvent(context, KeyEvent.ACTION_DOWN, keyCode);
        keyEvent(context, KeyEvent.ACTION_UP, keyCode);
    }

    private static void keyEvent(Context context, int keyEvent, int keyCode) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        long time = SystemClock.uptimeMillis();
        KeyEvent event = new KeyEvent(time, time, keyEvent, keyCode, 0);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, event);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.sendOrderedBroadcast(intent, null);
        getAudioManager(context).dispatchMediaKeyEvent(event);
    }


}
