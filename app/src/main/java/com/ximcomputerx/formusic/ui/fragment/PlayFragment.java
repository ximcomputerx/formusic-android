package com.ximcomputerx.formusic.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseFragment;
import com.ximcomputerx.formusic.config.Actions;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.enums.PlayModeEnum;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.LikeMusicInfo;
import com.ximcomputerx.formusic.model.LrcInfo;
import com.ximcomputerx.formusic.model.LrcListInfo;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.play.OnPlayerEventListener;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.activity.main.MainActivity;
import com.ximcomputerx.formusic.ui.adapter.PlayPagerAdapter;
import com.ximcomputerx.formusic.utils.FileUtils;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.utils.LogUtil;
import com.ximcomputerx.formusic.utils.ScreenUtils;
import com.ximcomputerx.formusic.utils.SharedPreferencesUtil;
import com.ximcomputerx.formusic.utils.SystemUtils;
import com.ximcomputerx.formusic.utils.ToastUtil;
import com.ximcomputerx.formusic.view.IndicatorLayout;

import org.litepal.LitePal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import me.wcy.lrcview.LrcView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR HACKER
 */
public class PlayFragment extends BaseFragment implements ViewPager.OnPageChangeListener, SeekBar.OnSeekBarChangeListener, OnPlayerEventListener,
        LrcView.OnPlayClickListener {
    public static final String TAG = "PlayFragment";
    @Bind(R.id.ll_content)
    protected LinearLayout llContent;
    @Bind(R.id.iv_play_page_bg)
    protected ImageView ivPlayingBg;
    @Bind(R.id.iv_back)
    protected ImageView ivBack;
    @Bind(R.id.tv_title)
    protected TextView tvTitle;
    @Bind(R.id.tv_artist)
    protected TextView tvArtist;
    @Bind(R.id.vp_play_page)
    protected ViewPager vpPlay;
    @Bind(R.id.il_indicator)
    protected IndicatorLayout ilIndicator;
    @Bind(R.id.sb_progress)
    protected SeekBar sbProgress;
    @Bind(R.id.tv_current_time)
    protected TextView tvCurrentTime;
    @Bind(R.id.tv_total_time)
    protected TextView tvTotalTime;
    @Bind(R.id.iv_mode)
    protected ImageView ivMode;
    @Bind(R.id.iv_play)
    protected ImageView ivPlay;
    @Bind(R.id.iv_next)
    protected ImageView ivNext;
    @Bind(R.id.iv_prev)
    protected ImageView ivPrev;
    @Bind(R.id.iv_list)
    protected ImageView iv_list;
    @Bind(R.id.iv_like)
    protected ImageView iv_like;
    private CircleImageView civ_image;
    private LrcView mLrcViewSingle;
    private LrcView mLrcViewFull;
    private SeekBar sbVolume;

    private AudioManager mAudioManager;
    private List<View> mViewPagerContent;
    private int mLastProgress;
    private boolean isDraggingProgress;

    private Animation rotateAnimation;

    /**
     * 广播接收者
     */
    private BroadcastReceiver mVolumeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_play;
    }

    @Override
    protected void initView(View contentView) {
        ImmersionBar.with(this).reset();
        ImmersionBar.with(this).navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();

        EventBus.getDefault().register(this);
        ScreenUtils.init(getContext());

        // 初始化动画
        initWheelAnimation();
        // 初始化状态栏
        initSystemBar();
        // 初始化播放界面
        initViewPager();

        ilIndicator.create(mViewPagerContent.size());

        initPlayMode();
        onChangeImpl(PlayManager.getInstance().getPlayMusic());

        PlayManager.getInstance().addOnPlayEventListener(this);
        sbProgress.setOnSeekBarChangeListener(this);
        vpPlay.addOnPageChangeListener(this);

        Glide.with(this).load(R.mipmap.play_page_default_cover)
                //.dontAnimate()
                //.placeholder(Color.GRAY)
                //.error(Color.GRAY)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CenterCrop())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(ivPlayingBg);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Actions.VOLUME_CHANGED_ACTION);
        getContext().registerReceiver(mVolumeReceiver, filter);
        onChangeImpl(PlayManager.getInstance().getPlayMusic());
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        getContext().unregisterReceiver(mVolumeReceiver);
        PlayManager.getInstance().removeOnPlayEventListener(this);
        super.onDestroy();
    }

    /**
     * EVENBUS RECIVER
     * @param messageEvent
     */
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessageId()) {
            case EventId.EVENT_ID_MUSIC_TIME:
                sbProgress.setMax((int) PlayManager.getInstance().getPlayMusic().getDuration());
                tvTotalTime.setText(formatTime((int) PlayManager.getInstance().getPlayMusic().getDuration()));
                break;
            case EventId.EVENT_ID_MUSIC_LIST:
                PlayManager.getInstance().setMusicList(0);
                break;
        }
    }

    /**
     * 沉浸式状态栏
     */
    private void initSystemBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ScreenUtils.init(getContext());
            int top = ScreenUtils.getStatusBarHeight();
            llContent.setPadding(0, top, 0, 0);
        }
    }

    private void initViewPager() {
        View coverView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_play_page_cover, null);
        View lrcView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_play_page_lrc, null);

        civ_image = coverView.findViewById(R.id.civ_image);
        mLrcViewSingle = coverView.findViewById(R.id.lrc_view_single);
        mLrcViewFull = lrcView.findViewById(R.id.lrc_view_full);
        sbVolume = lrcView.findViewById(R.id.sb_volume);

        sbVolume.setOnSeekBarChangeListener(this);
        mLrcViewFull.setOnPlayClickListener(this);

        // 初始化音量
        initVolume();

        mViewPagerContent = new ArrayList<>(2);
        mViewPagerContent.add(coverView);
        mViewPagerContent.add(lrcView);
        vpPlay.setAdapter(new PlayPagerAdapter(mViewPagerContent));
    }

    private void initVolume() {
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        sbVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    private void initPlayMode() {
        int mode = SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, 0);
        ivMode.setImageLevel(mode);
    }

    private void initLikeData(MusicInfo music) {
        iv_like.setImageResource(R.mipmap.song_group_page_collect_n);
        List<LikeMusicInfo> likeMusicInfos = LitePal.findAll(LikeMusicInfo.class);
        for (LikeMusicInfo likeMusicInfo : likeMusicInfos) {
            if (likeMusicInfo.getSongId() == music.getSongId()) {
                iv_like.setImageResource(R.mipmap.song_group_page_collect_p);
            }
        }
    }

    private void onChangeImpl(MusicInfo music) {
        if (music == null) {
            return;
        }

        tvTitle.setText(music.getTitle());
        tvArtist.setText(music.getArtist());
        sbProgress.setProgress((int) PlayManager.getInstance().getAudioPosition());
        sbProgress.setSecondaryProgress(0);
        sbProgress.setMax((int) music.getDuration());
        mLastProgress = 0;
        tvCurrentTime.setText(R.string.play_time_start);
        tvTotalTime.setText(formatTime(music.getDuration()));
        setBackground(music);
        setCoverAndBg(music);
        setLrc(music);
        if (PlayManager.getInstance().isPlaying() || PlayManager.getInstance().isPreparing()) {
            ivPlay.setSelected(true);
            civ_image.startAnimation(rotateAnimation);
            LogUtil.e(TAG, "onChangeImpl onPlayerPause().......");
        } else {
            ivPlay.setSelected(false);
            civ_image.clearAnimation();
            LogUtil.e(TAG, "onChangeImpl onPlayerPause().......");
        }
        initLikeData(music);
    }

    private void initWheelAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_wheel);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setFillAfter(true);
    }

    @OnClick({R.id.iv_back, R.id.iv_mode, R.id.iv_play, R.id.iv_next, R.id.iv_prev, R.id.iv_list, R.id.iv_like, R.id.iv_download})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_mode:
                switchPlayMode();
                break;
            case R.id.iv_play:
                play();
                break;
            case R.id.iv_next:
                next();
                break;
            case R.id.iv_prev:
                prev();
                break;
            case R.id.iv_list:
                ((MainActivity) context).playList(MainActivity.DIALOG_PLAY);
                break;
            case R.id.iv_like:
                // 发送收藏歌曲事件
                //EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_LIKE, ""));
                MusicInfo musicInfoTemp = PlayManager.getInstance().getPlayMusic();
                LikeMusicInfo likeMusicInfo = new LikeMusicInfo();
                toMusic(likeMusicInfo, musicInfoTemp);
                List<LikeMusicInfo> likeMusicInfos = LitePal.where("songId=?", likeMusicInfo.getSongId() + "").find(LikeMusicInfo.class);
                if (likeMusicInfos != null && likeMusicInfos.size() >= 1) {
                    LitePal.deleteAll(LikeMusicInfo.class, "songId=?", likeMusicInfo.getSongId() + "");
                    iv_like.setImageResource(R.mipmap.song_group_page_collect_n);
                } else {
                    likeMusicInfo.save();
                    iv_like.setImageResource(R.mipmap.song_group_page_collect_p);
                }
                break;
            case R.id.iv_download:
                break;
        }
    }

    private void toMusic(LikeMusicInfo temp, MusicInfo musicInfo) {
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
    }

    private void onBackPressed() {
        getActivity().onBackPressed();
        ivBack.setEnabled(false);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBack.setEnabled(true);
            }
        }, 300);
    }

    private void switchPlayMode() {
        PlayModeEnum mode = PlayModeEnum.valueOf(SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, 0));
        switch (mode) {
            case LOOP:
                mode = PlayModeEnum.SHUFFLE;
                ToastUtil.showShort(getContext(), getContext().getString(R.string.mode_shuffle));
                break;
            case SHUFFLE:
                mode = PlayModeEnum.SINGLE;
                ToastUtil.showShort(getContext(), getContext().getString(R.string.mode_one));
                break;
            case SINGLE:
                mode = PlayModeEnum.LOOP;
                ToastUtil.showShort(getContext(), getContext().getString(R.string.mode_loop));
                break;
        }
        SharedPreferencesUtil.setIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, mode.value());
        initPlayMode();
    }

    private void play() {
        PlayManager.getInstance().playPause();
    }

    private void next() {
        PlayManager.getInstance().next();
    }

    private void prev() {
        PlayManager.getInstance().prev();
    }


    /**
     * 设置封面
     * @param music
     */
    private void setCoverAndBg(MusicInfo music) {
        GlideImageLoaderUtil.displayImage(music.getCoverPath(), civ_image, R.mipmap.play_page_default_cover);

    }

    private void setBackground(MusicInfo music) {
        Glide.with(this).load(music.getCoverPath())
                //.dontAnimate()
                //.placeholder(Color.GRAY)
                //.error(Color.GRAY)
                .thumbnail(Glide.with(this).load(R.mipmap.play_page_default_cover)
                        .transform(new CenterCrop())
                        .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3))))
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new CenterCrop())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(ivPlayingBg);
    }

    @Override
    public void onChange(MusicInfo music) {
        onChangeImpl(music);
    }

    @Override
    public void onPlayerStart() {
        ivPlay.setSelected(true);
        civ_image.startAnimation(rotateAnimation);
        LogUtil.e(TAG, "onPlayerStart().......");
    }

    @Override
    public void onPlayerPause() {
        ivPlay.setSelected(false);
        civ_image.clearAnimation();
        LogUtil.e(TAG, "onPlayerPause().......");
    }

    /**
     * 更新播放进度
     */
    @Override
    public void onPublish(int progress) {
        if (!isDraggingProgress) {
            sbProgress.setProgress(progress);
        }

        if (mLrcViewSingle.hasLrc()) {
            mLrcViewSingle.updateTime(progress);
            mLrcViewFull.updateTime(progress);
        }
    }

    @Override
    public void onBufferingUpdate(int percent) {
        Log.e("onBufferingUpdate", percent + "");
        sbProgress.setSecondaryProgress(sbProgress.getMax() * 100 / percent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == sbProgress) {
            if (Math.abs(progress - mLastProgress) >= DateUtils.SECOND_IN_MILLIS) {
                tvCurrentTime.setText(formatTime(progress));
                mLastProgress = progress;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar == sbProgress) {
            isDraggingProgress = true;
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar == sbProgress) {
            isDraggingProgress = false;
            if (PlayManager.getInstance().isPlaying() || PlayManager.getInstance().isPausing()) {
                int progress = seekBar.getProgress();
                PlayManager.getInstance().seekTo(progress);

                if (mLrcViewSingle.hasLrc()) {
                    mLrcViewSingle.updateTime(progress);
                    mLrcViewFull.updateTime(progress);
                }
            } else {
                seekBar.setProgress(0);
            }
        } else if (seekBar == sbVolume) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBar.getProgress(),
                    AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ilIndicator.setCurrent(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onPlayClick(long time) {
        if (PlayManager.getInstance().isPlaying() || PlayManager.getInstance().isPausing()) {
            PlayManager.getInstance().seekTo((int) time);
            if (PlayManager.getInstance().isPausing()) {
                PlayManager.getInstance().playPause();
            }
            return true;
        }
        return false;
    }

    private void setLrc(final MusicInfo music) {
        if (music.getType() == MusicInfo.Type.LOCAL) {
            String lrcPath = FileUtils.getLrcFilePath(music);
            if (!TextUtils.isEmpty(lrcPath)) {
                loadLrc(lrcPath);
            } else {
                // TODO: 2020/1/10  
                /*new SearchLrc(music.getArtist(), music.getTitle()) {
                    @Override
                    public void onPrepare() {
                        // 设置tag防止歌词下载完成后已切换歌曲
                        vpPlay.setTag(music);

                        loadLrc("");
                        setLrcLabel("正在搜索歌词");
                    }

                    @Override
                    public void onExecuteSuccess(@NonNull String lrcPath) {
                        if (vpPlay.getTag() != music) {
                            return;
                        }

                        // 清除tag
                        vpPlay.setTag(null);

                        loadLrc(lrcPath);
                        setLrcLabel("暂无歌词");
                    }

                    @Override
                    public void onExecuteFail(Exception e) {
                        if (vpPlay.getTag() != music) {
                            return;
                        }

                        // 清除tag
                        vpPlay.setTag(null);

                        setLrcLabel("暂无歌词");
                    }
                }.execute();*/
            }
        } else {
            //String lrcPath = FileUtils.getLrcDir() + FileUtils.getLrcFileName(music.getArtist(), music.getTitle());
            //loadLrc(lrcPath);
            setLrcLabel("暂无歌词");
            getApiWrapper(false).lrcList(music.getSongId() + "")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LrcListInfo<LrcInfo>>() {
                        @Override
                        public void onCompleted() {
                            //closeNetDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //closeNetDialog();
                            //ToastUtil.showToast(getResources().getString(R.string.load_error));
                        }

                        @Override
                        public void onNext(LrcListInfo<LrcInfo> lrcListInfo) {

                            if (lrcListInfo != null) {
                                String lrc = lrcListInfo.getLrc().getLyric();
                                mLrcViewSingle.loadLrc(lrc);
                                mLrcViewFull.loadLrc(lrc);
                            } else {

                            }
                        }
                    });
        }
    }

    private String formatTime(long time) {
        return SystemUtils.formatTime("mm:ss", time);
    }

    private void loadLrc(String path) {
        File file = new File(path);
        mLrcViewSingle.loadLrc(file);
        mLrcViewFull.loadLrc(file);
    }

    private void setLrcLabel(String label) {
        mLrcViewSingle.setLabel(label);
        mLrcViewFull.setLabel(label);
    }
}
