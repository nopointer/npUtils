package npUtils.nopointer.control;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;

import static android.media.AudioManager.ADJUST_LOWER;
import static android.media.AudioManager.ADJUST_RAISE;
import static android.media.AudioManager.FLAG_SHOW_UI;

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
    public static boolean isMusicPlaying(Context context) {
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
     * 停止
     *
     * @param context
     */
    public static void stop(Context context) {
        sendOrderedBroadcast(context, KeyEvent.KEYCODE_MEDIA_STOP);
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
     * 获取多媒体最大音量
     * @param context
     * @return
     */
    public static int getMediaMaxVolume(Context context) {
        return getAudioManager(context).getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 获取多媒体音量
     * @param context
     * @return
     */
    public static  int getMediaVolume(Context context) {
        return getAudioManager(context).getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    //获取通话最大音量
    public static  int getCallMaxVolume(Context context) {
        return getAudioManager(context).getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
    }

    /**
     * 获取系统音量最大值
     * @param context
     * @return
     */
    public static  int getSystemMaxVolume(Context context) {
        return getAudioManager(context).getStreamMaxVolume(AudioManager.STREAM_SYSTEM);
    }

    /**
     * 获取系统音量
     * @param context
     * @return
     */
    public static  int getSystemVolume(Context context) {
        return getAudioManager(context).getStreamVolume(AudioManager.STREAM_SYSTEM);
    }

    /**
     * 获取提示音量最大值
     * @param context
     * @return
     */
    public  static int getAlermMaxVolume(Context context) {
        return getAudioManager(context).getStreamMaxVolume(AudioManager.STREAM_ALARM);
    }

    /**
     * 设置多媒体音量
     * 这里我只写了多媒体和通话的音量调节，其他的只是参数不同，大家可仿照
     */
    public static  void setMediaVolume(Context context,int volume) {
        getAudioManager(context).setStreamVolume(AudioManager.STREAM_MUSIC, //音量类型
                volume,
                AudioManager.FLAG_PLAY_SOUND
                        | FLAG_SHOW_UI);
    }

    /**
     * 设置通话音量
     * @param context
     * @param volume
     */
    public  static void setCallVolume(Context context,int volume) {
        getAudioManager(context).setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                volume,
                AudioManager.STREAM_VOICE_CALL);
    }

    /**
     * 关闭/打开扬声器播放
     * @param context
     * @param on
     */
    public  static void setSpeakerStatus(Context context, boolean on) {
        AudioManager audioManager = getAudioManager(context);
        if (on) { //扬声器
            audioManager.setSpeakerphoneOn(true);
            audioManager.setMode(AudioManager.MODE_NORMAL);
        } else {
            // 设置最大音量
            int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL);
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, max, AudioManager.STREAM_VOICE_CALL);
            // 设置成听筒模式
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            audioManager.setSpeakerphoneOn(false);// 关闭扬声器
            audioManager.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
        }
    }

    /**
     * 调整音量
     *
     * @param isAdd true 表示+ false 表示-
     */
    public static  void adjustStreamVolume(Context context, boolean isAdd) {
        getAudioManager(context).adjustStreamVolume(AudioManager.STREAM_MUSIC,
                isAdd ? ADJUST_RAISE : ADJUST_LOWER, 0);
    }

    /**
     * 是否开启静音模式
     *
     * @param context
     * @param isMuteMode
     */
    public static  void setMuteMode(Context context, boolean isMuteMode) {
        AudioManager audioManager = getAudioManager(context);
        if (isMuteMode) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            Log.e("", "RINGING 已被静音");
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            Log.e("", "RINGING 已关闭静音");
        }
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
