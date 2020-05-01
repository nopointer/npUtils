package npUtils.nopointer.control.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * 手机工具
 * 可以接听 挂断来电
 * 分别处理了高版本和低版本
 * 需要权限
 * <p>
 * <uses-permission android:name="android.permission.ANSWER_PHONE_CALLS" />
 * <uses-permission android:name="android.permission.CALL_PHONE" />
 */
public class PhoneUtils {
    private static final String TAG = "ta";

    private PhoneUtils() {
    }

    /**
     * 结束/挂断电话(来电拒绝)
     *
     * @param context
     */
    public static void endCall(Context context) {
        //新版本的挂断方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            TelecomManager tm = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            if (tm != null) {
                tm.endCall();
            }
        } else {
            //旧版本的挂断方式
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            Class<TelephonyManager> c = TelephonyManager.class;
            try {
                Method getITelephonyMethod = c.getDeclaredMethod("getITelephony");
                getITelephonyMethod.setAccessible(true);
                Log.e("bleService", "End call.");
                ITelephony iTelephony = (ITelephony) getITelephonyMethod.invoke(telephonyManager);
                iTelephony.endCall();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("bleService", "Fail to answer ring call.", e);
            }
        }
    }


    /**
     * 接听电话
     *
     * @param context
     */
    public static void answerCall(Context context) {
        //新版本的挂断方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            TelecomManager tm = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            if (tm != null) {
                tm.acceptRingingCall();
            }
        } else {
            try {
                Log.e(TAG, "用于Android2.3及2.3以上的版本上");
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                context.sendOrderedBroadcast(intent,
                        "android.permission.CALL_PRIVILEGED");
                intent = new Intent("android.intent.action.MEDIA_BUTTON");
                keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                context.sendOrderedBroadcast(intent,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent1.putExtra("state", 1);
                localIntent1.putExtra("microphone", 1);
                localIntent1.putExtra("name", "Headset");
                context.sendOrderedBroadcast(localIntent1, "android.permission.CALL_PRIVILEGED");
                Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent2.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent1);
                context.sendOrderedBroadcast(localIntent2,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
                KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK);
                localIntent3.putExtra("android.intent.extra.KEY_EVENT",
                        localKeyEvent2);
                context.sendOrderedBroadcast(localIntent3,
                        "android.permission.CALL_PRIVILEGED");
                Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
                localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                localIntent4.putExtra("state", 0);
                localIntent4.putExtra("microphone", 1);
                localIntent4.putExtra("name", "Headset");
                context.sendOrderedBroadcast(localIntent4,
                        "android.permission.CALL_PRIVILEGED");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Intent meidaButtonIntent = new Intent(
                            Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP,
                            KeyEvent.KEYCODE_HEADSETHOOK);
                    meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                    context.sendOrderedBroadcast(meidaButtonIntent, null);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }


}
