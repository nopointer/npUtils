package npUtils.nopointer.control.system;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SystemUtils {
    //关机
    public static void shutdown(Context context) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            try {
                Class<?> serviceManager = Class.forName("android.os.ServiceManager");
                Method getService = serviceManager.getMethod("getService", String.class);
                Object remoteService = getService.invoke(null, Context.POWER_SERVICE);
                Class<?> stub = Class.forName("android.os.IPowerManager$Stub");
                Method asInterface = stub.getMethod("asInterface", IBinder.class);
                Object powerManager = asInterface.invoke(null, remoteService);
                Method shutdown = powerManager.getClass().getDeclaredMethod("shutdown",
                        boolean.class, String.class, boolean.class);
                shutdown.invoke(powerManager, false, "", true);
            } catch (Exception e) {
                //nothing to do
            }
        } else {
            Intent shutdown = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
            shutdown.putExtra("android.intent.extra.KEY_CONFIRM", false);
            shutdown.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(shutdown);
        }
    }

    //重启
    public static void reboot(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        pm.reboot("");
    }

    //定时开机
    public static void setPowerOffAlarm(Context contetx) {
        //定时开机时间,时间设置最好不要小于一分钟，我此处设置为90 * 1000毫秒
        long time = System.currentTimeMillis() + 90 * 1000;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            Intent intent = new Intent("org.codeaurora.poweroffalarm.action.SET_ALARM");
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            intent.setPackage("com.qualcomm.qti.poweroffalarm");
            intent.putExtra("time", time);
            contetx.sendBroadcast(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_BOOT_COMPLETED);
            intent.putExtra("seq", 1);
            PendingIntent pi = PendingIntent.getBroadcast(contetx, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) contetx.getSystemService(
                    Context.ALARM_SERVICE);
            int type = 8;
            try {
                Field field = AlarmManager.class.getField("RTC_POWEROFF_WAKEUP");
                type = field.getInt(null);
            } catch (Exception e) {
                //nothing to do
            }
            alarmManager.setExact(type, time, pi);
        }
    }
}