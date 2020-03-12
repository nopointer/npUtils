package demo.nopointer.npUtils;

import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

public class MediaPlaybackService extends MediaBrowserServiceCompat {

  @Override

  public void onCreate() {

    super.onCreate();

    // 1. 初始化 MediaSession
        mSession = new MediaSessionCompat(this, "MusicService");

    // 2. 设置 MedisSessionCallback
        mSession.setCallback(mSessionCallback);

    // 3. 开启 MediaButton 和 TransportControls 的支持
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                 MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

    // 4. 初始化 PlaybackState
         mStateBuilder = new PlaybackStateCompat.Builder()
                 .setActions(
                         PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PLAY_PAUSE);
         mSession.setPlaybackState(mStateBuilder.build());

    // 5. 关联 SessionToken
         setSessionToken(mSession.getSessionToken());
  }
}
