package com.ximcomputerx.formusic.executor;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.play.OnPlayerEventListener;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.activity.main.MainActivity;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ControlPanel implements View.OnClickListener, OnPlayerEventListener {
    @Bind(R.id.iv_play_bar_cover)
    protected CircleImageView ivPlayBarCover;
    @Bind(R.id.tv_play_bar_title)
    protected TextView tvPlayBarTitle;
    @Bind(R.id.tv_play_bar_artist)
    protected TextView tvPlayBarArtist;
    @Bind(R.id.iv_play_bar_play)
    protected ImageView ivPlayBarPlay;
    @Bind(R.id.iv_play_bar_next)
    protected ImageView ivPlayBarNext;
    @Bind(R.id.iv_play_bar_playlist)
    protected ImageView vPlayBarPlaylist;
    @Bind(R.id.pb_play_bar)
    protected ProgressBar mProgressBar;

    private Animation rotateAnimation;

    private Context context;

    public ControlPanel(Context context, View view) {
        this.context = context;
        ButterKnife.bind(this, view);

        initWheelAnimation();

        ivPlayBarPlay.setOnClickListener(this);
        ivPlayBarNext.setOnClickListener(this);
        vPlayBarPlaylist.setOnClickListener(this);
        onChange(PlayManager.getInstance().getPlayMusic());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play_bar_play:
                PlayManager.getInstance().playPause();
                break;
            case R.id.iv_play_bar_next:
                PlayManager.getInstance().next();
                break;
            case R.id.iv_play_bar_playlist:
                ((MainActivity) context).playList(MainActivity.DIALOG_MAIN);
                break;
        }
    }

    private void initWheelAnimation() {
        rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_wheel);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setFillAfter(true);

    }

    @Override
    public void onChange(MusicInfo music) {
        if (music == null) {
            return;
        }
        GlideImageLoaderUtil.displayImage(music.getCoverPath(), ivPlayBarCover, R.mipmap.default_cover);
        tvPlayBarTitle.setText(music.getTitle());
        tvPlayBarArtist.setText(music.getArtist());
        ivPlayBarPlay.setSelected(PlayManager.getInstance().isPlaying() || PlayManager.getInstance().isPreparing());
        mProgressBar.setMax((int) music.getDuration());
        mProgressBar.setProgress((int) PlayManager.getInstance().getAudioPosition());
    }

    @Override
    public void onPlayerStart() {
        ivPlayBarPlay.setSelected(true);
        ivPlayBarCover.startAnimation(rotateAnimation);
    }

    @Override
    public void onPlayerPause() {
        ivPlayBarPlay.setSelected(false);
        ivPlayBarCover.clearAnimation();
    }

    @Override
    public void onPublish(int progress) {
        mProgressBar.setProgress(progress);

    }

    @Override
    public void onBufferingUpdate(int percent) {

    }

    public ProgressBar getmProgressBar() {
        return mProgressBar;
    }

    public void setmProgressBar(ProgressBar mProgressBar) {
        this.mProgressBar = mProgressBar;
    }
}
