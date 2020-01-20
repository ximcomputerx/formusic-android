package com.ximcomputerx.formusic.play;

import com.ximcomputerx.formusic.model.MusicInfo;

/**
 * 播放进度监听器
 */
public interface OnPlayerEventListener {

    /**
     * 切换歌曲
     */
    void onChange(MusicInfo music);

    /**
     * 继续播放
     */
    void onPlayerStart();

    /**
     * 暂停播放
     */
    void onPlayerPause();

    /**
     * 更新进度
     */
    void onPublish(int progress);

    /**
     * 缓冲百分比
     */
    void onBufferingUpdate(int percent);
}
