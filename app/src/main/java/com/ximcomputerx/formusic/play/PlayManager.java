package com.ximcomputerx.formusic.play;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;

import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.enums.PlayModeEnum;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.HistoryMusicInfo;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.notice.NoticeManager;
import com.ximcomputerx.formusic.receiver.NoisyAudioStreamReceiver;
import com.ximcomputerx.formusic.util.SharedPreferencesUtil;
import com.ximcomputerx.formusic.util.ToastUtil;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * @CREATED HACKER
 */
public class PlayManager {
    private static final String TAG = "PlayManager";

    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PLAYING = 2;
    private static final int STATE_PAUSE = 3;

    private static final long TIME_UPDATE = 300L;

    private Handler handler;
    private Context context;
    private AudioFocusManager audioFocusManager;
    private MediaPlayer mediaPlayer;
    private NoisyAudioStreamReceiver noisyReceiver;
    private IntentFilter noisyFilter;
    private List<MusicInfo> musicList;
    private final List<OnPlayerEventListener> listeners = new ArrayList<>();
    private int state = STATE_IDLE;

    /**
     * 获取播放控制器
     * @return
     */
    public static PlayManager getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 单实例播放控制器
     */
    private static class SingletonHolder {
        private static PlayManager instance = new PlayManager();
    }

    /**
     * 构造函数
     */
    private PlayManager() {
    }

    /**
     * 播放控制器初始化
     * @param context
     */
    public void init(Context context) {
        this.context = context.getApplicationContext();
        // 查找播放列表
        musicList = LitePal.findAll(MusicInfo.class);
        // 创建音频焦点管理器
        audioFocusManager = new AudioFocusManager(context);
        // 创建媒体播放器
        mediaPlayer = new MediaPlayer();
        // 创建消息处理器
        handler = new Handler(Looper.getMainLooper());
        // 来电耳机拔出时广播接受者
        noisyReceiver = new NoisyAudioStreamReceiver();
        // 来电拔出时过滤器
        noisyFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

        // 当流媒体播放完毕的时候回调
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                next();
            }
        });
        // 当装载流媒体完毕的时候回调
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (isPreparing()) {
                    getPlayMusic().setDuration(mediaPlayer.getDuration());
                    EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_TIME, mediaPlayer.getDuration()));
                    startPlayer();
                }
            }
        });
        // 网络上的媒体资源缓存进度更新的时候会触发
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                for (OnPlayerEventListener listener : listeners) {
                    //LogUtil.e(TAG, "onBufferingUpdate():" + percent);
                    listener.onBufferingUpdate(percent);
                }
            }
        });
        palyFlag(getPlayPosition());
    }

    /**
     * 是否是播放状态
     * @return
     */
    public boolean isPlaying() {
        return state == STATE_PLAYING;
    }

    /**
     * 是否是暂停状态
     * @return
     */
    public boolean isPausing() {
        return state == STATE_PAUSE;
    }

    /**
     * 是否是准备状态
     * @return
     */
    public boolean isPreparing() {
        return state == STATE_PREPARING;
    }

    /**
     * 是否是空闲状态
     * @return
     */
    public boolean isIdle() {
        return state == STATE_IDLE;
    }

    /**
     * 添加播放事件监听器
     * @param listener
     */
    public void addOnPlayEventListener(OnPlayerEventListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }


    /**
     * 移除播放事件监听器
     * @param listener
     */
    public void removeOnPlayEventListener(OnPlayerEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * 播放
     * @param position
     */
    public void play(int position) {
        if (musicList.isEmpty()) {
            return;
        }

        if (position < 0) {
            position = musicList.size() - 1;
        } else if (position >= musicList.size()) {
            position = 0;
        }

        setPlayPosition(position);
        MusicInfo music = getPlayMusic();

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(music.getPath());
            mediaPlayer.prepareAsync();
            state = STATE_PREPARING;
            for (OnPlayerEventListener listener : listeners) {
                listener.onChange(music);
            }
            // TODO
            NoticeManager.getInstance().showPlay(music);
            MediaSessionManager.getInstance().updateMetaData(music);
            MediaSessionManager.getInstance().updatePlaybackState();
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.showShortToast("当前歌曲无法播放");
        }
    }

    /**
     * 播放暂停
     */
    public void playPause() {
        if (isPreparing()) {
            stopPlayer();
        } else if (isPlaying()) {
            pausePlayer();
        } else if (isPausing()) {
            startPlayer();
        } else {
            play(getPlayPosition());
            // 发送事件播放历史事件
            //EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_HISTORY, ""));

            MusicInfo musicInfo = PlayManager.getInstance().getPlayMusic();

            HistoryMusicInfo historyMusicInfo = new HistoryMusicInfo();

            toMusic(historyMusicInfo, musicInfo);

            LitePal.deleteAll(HistoryMusicInfo.class, "songId=?", historyMusicInfo.getSongId() + "");
            historyMusicInfo.save();
        }
    }

    private void toMusic(MusicInfo temp, MusicInfo musicInfo) {
        temp.setType(musicInfo.getType());
        temp.setSongId(musicInfo.getSongId());
        temp.setCoverPath(musicInfo.getCoverPath());
        temp.setTitle(musicInfo.getTitle());
        temp.setArtist(musicInfo.getArtist());
        temp.setAlbum(musicInfo.getAlbum());
        temp.setAlbumId(musicInfo.getAlbumId());
        temp.setDuration(musicInfo.getDuration());
        temp.setPath(musicInfo.getPath());
        temp.setFileName(musicInfo.getFileName());
        temp.setFileSize(musicInfo.getFileSize());
        temp.setFee(musicInfo.getFee());
    }

    /**
     * 停止播放
     */
    public void stopPlayer() {
        if (isIdle()) {
            return;
        }

        //pausePlayer();
        mediaPlayer.stop();
        mediaPlayer.reset();
        state = STATE_IDLE;

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }

        //NoticeManager.getInstance().showPause(getPlayMusic());
        //MediaSessionManager.getInstance().updatePlaybackState();
    }

    /**
     * 暂停播放
     */
    public void pausePlayer() {
        pausePlayer(true);
    }

    /**
     * 暂停播放
     * @param abandonAudioFocus
     */
    public void pausePlayer(boolean abandonAudioFocus) {
        if (!isPlaying()) {
            return;
        }
        mediaPlayer.pause();
        state = STATE_PAUSE;
        handler.removeCallbacks(mPublishRunnable);
        // TODO
        NoticeManager.getInstance().showPause(getPlayMusic());
        MediaSessionManager.getInstance().updatePlaybackState();
        context.unregisterReceiver(noisyReceiver);
        if (abandonAudioFocus) {
            audioFocusManager.abandonAudioFocus();
        }

        for (OnPlayerEventListener listener : listeners) {
            listener.onPlayerPause();
        }
    }

    /**
     * 开始播放
     */
    public void startPlayer() {
        if (!isPreparing() && !isPausing()) {
            return;
        }

        if (audioFocusManager.requestAudioFocus()) {
            mediaPlayer.start();
            state = STATE_PLAYING;
            handler.post(mPublishRunnable);
            // TODO
            NoticeManager.getInstance().showPlay(getPlayMusic());
            MediaSessionManager.getInstance().updatePlaybackState();
            context.registerReceiver(noisyReceiver, noisyFilter);

            for (OnPlayerEventListener listener : listeners) {
                listener.onPlayerStart();
            }
        }
    }

    /**
     * 下一首歌曲
     */
    public void next() {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, 0));
        switch (mode) {
            case SHUFFLE:
                play(new Random().nextInt(musicList.size()));
                break;
            case SINGLE:
                play(getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() + 1);
                break;
        }
    }

    /**
     * 上一首歌曲
     */
    public void prev() {
        if (musicList.isEmpty()) {
            return;
        }

        PlayModeEnum mode = PlayModeEnum.valueOf(SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, 0));
        switch (mode) {
            case SHUFFLE:
                play(new Random().nextInt(musicList.size()));
                break;
            case SINGLE:
                play(getPlayPosition());
                break;
            case LOOP:
            default:
                play(getPlayPosition() - 1);
                break;
        }
    }

    /**
     * 添加到播放列表
     * @param musicInfo
     */
    public void addAndPlay(MusicInfo musicInfo) {
        int position = musicList.indexOf(musicInfo);
        if (position < 0) {
            musicList.add(musicInfo);
            musicInfo.save();
            position = musicList.size() - 1;
        }
        play(position);
    }

    /**
     * 从播放列表删除
     * @param position
     */
    public void delete(int position) {
        int playPosition = getPlayPosition();
        MusicInfo music = musicList.remove(position);
        LitePal.delete(MusicInfo.class, music.getId());
        if (playPosition > position) {
            setPlayPosition(playPosition - 1);
        } else if (playPosition == position) {
            if (isPlaying() || isPreparing()) {
                setPlayPosition(playPosition - 1);
                next();
            } else {
                stopPlayer();
                for (OnPlayerEventListener listener : listeners) {
                    listener.onChange(getPlayMusic());
                }
            }
        }
    }

    /**
     * 跳转到指定的时间位置
     * @param msec 时间
     */
    public void seekTo(int msec) {
        if (isPlaying() || isPausing()) {
            mediaPlayer.seekTo(msec);
            MediaSessionManager.getInstance().updatePlaybackState();
            for (OnPlayerEventListener listener : listeners) {
                listener.onPublish(msec);
            }
        }
    }

    /**
     * 更新进度处理器
     */
    private Runnable mPublishRunnable = new Runnable() {
        @Override
        public void run() {
            if (isPlaying()) {
                for (OnPlayerEventListener listener : listeners) {
                    listener.onPublish(mediaPlayer.getCurrentPosition());
                }
            }
            handler.postDelayed(this, TIME_UPDATE);
        }
    };

    /**
     * 获取播放回话id
     * @return
     */
    public int getAudioSessionId() {
        return mediaPlayer.getAudioSessionId();
    }

    /**
     * 获取播放位置
     * @return
     */
    public long getAudioPosition() {
        if (isPlaying() || isPausing()) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    /**
     * 获取播放音乐
     * @return
     */
    public MusicInfo getPlayMusic() {
        if (musicList.isEmpty()) {
            return null;
        }
        return musicList.get(getPlayPosition());
    }

    /**
     * 获取播放器
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * 设置播放列表
     * @return
     */
    public void setMusicList(int position) {
        musicList = LitePal.findAll(MusicInfo.class);
        setPlayPosition(position);
        stopPlayer();
        playPause();
    }
    /**
     * 设置播放列表
     * @return
     */
    public void setMusicList(List<MusicInfo> musicLists, int position) {
        this.musicList = musicLists;
        setPlayPosition(position);
        stopPlayer();
        playPause();
    }

    /**
     * 获取播放列表
     * @return
     */
    public List<MusicInfo> getMusicList() {
        return musicList;
    }

    /**
     * 设置播放位置
     * @param position
     */
    public void setPlayPosition(int position) {
        SharedPreferencesUtil.setIntPreferences(Constant.PREFERENCES, Constant.PLAY_POSITION, position);
        palyFlag(position);
    }

    /**
     * 获取播放位置
     * @return
     */
    public int getPlayPosition() {
        int position = SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_POSITION, 0);
        if (position < 0 || position >= musicList.size()) {
            position = 0;
            SharedPreferencesUtil.setIntPreferences(Constant.PREFERENCES, Constant.PLAY_POSITION, position);
        }
        return position;
    }

    /**
     * 设置播放标记
     * @param position
     */
    public void palyFlag(int position) {
        for (int i = 0; i < musicList.size(); i++) {
            if (i == position) {
                musicList.get(i).setPlay(true);
            } else {
                musicList.get(i).setPlay(false);
            }
        }
    }

}
