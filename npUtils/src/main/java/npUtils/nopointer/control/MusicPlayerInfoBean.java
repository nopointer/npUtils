package npUtils.nopointer.control;

import android.content.ComponentName;

/**
 * 第三方音乐播放器的Play信息
 */
public class MusicPlayerInfoBean {


    public MusicPlayerInfoBean() {
    }

    public MusicPlayerInfoBean(String packName, String comReceiverName) {
        this.packName = packName;
        this.comReceiverName = comReceiverName;
    }

    //音乐播放器的包名
    private String packName;

    //需要启动的ComponentName
    private String comReceiverName;

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getComReceiverName() {
        return comReceiverName;
    }

    public void setComReceiverName(String comReceiverName) {
        this.comReceiverName = comReceiverName;
    }

    public ComponentName getComponentName() {
        ComponentName componentName = new ComponentName(packName, comReceiverName);
        return componentName;
    }

}
