package npUtils.nopointer.log;

import android.text.TextUtils;
import android.util.Log;

public class NpUtilsLog {


    /**
     * 是否允许打印日志
     */
    public static boolean logEnable = true;


    public static void log(String message) {
        if (mNpBleLogPrinter != null) {
            log(mNpBleLogPrinter.initTag(), message);
        } else {
            log("NpUtilsLog", message);
        }
    }

    public static void log(String tag, String message) {
        if (TextUtils.isEmpty(tag)) {
            tag = "NpUtilsLog";
        }
        if (mNpBleLogPrinter == null) {
            if (logEnable) {
                Log.e(tag, message);
            }
        } else {
            mNpBleLogPrinter.onLogPrint(tag, message);
        }
    }

    private static NpBleLogPrinter mNpBleLogPrinter;

    public static void setNpBleLogPrinter(NpBleLogPrinter npBleLogPrinter) {
        mNpBleLogPrinter = npBleLogPrinter;
    }

    public static interface NpBleLogPrinter {
        void onLogPrint(String message);

        void onLogPrint(String tag, String message);

        String initTag();
    }


}
