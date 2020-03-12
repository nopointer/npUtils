package npUtils.nopointer.control;

import java.io.IOException;

/**
 * 按键工具
 */
public class KeyEventUtils {
    private KeyEventUtils() {
    }


    /**
     * 模拟一个按键
     *
     * @param KeyCode
     */
    public static void sendKeyEvent(final int KeyCode) {
        try {
            String keyCommand = "input keyevent " + KeyCode;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(keyCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
