package com.ximcomputerx.formusic.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ximcomputerx.formusic.play.PlayManager;

/**
 * 来电/耳机拔出是暂停播放
 */
public class NoisyAudioStreamReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PlayManager.getInstance().playPause();
    }
}
