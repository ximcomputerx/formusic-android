package com.ximcomputerx.formusic.ui.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.config.Actions;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.executor.ControlPanel;
import com.ximcomputerx.formusic.model.HistoryMusicInfo;
import com.ximcomputerx.formusic.model.LikeMusicInfo;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.service.PlayService;
import com.ximcomputerx.formusic.ui.activity.search.SearchActivity;
import com.ximcomputerx.formusic.ui.activity.setting.AboutActivity;
import com.ximcomputerx.formusic.ui.activity.setting.IssuesActivity;
import com.ximcomputerx.formusic.ui.activity.setting.ScanActivity;
import com.ximcomputerx.formusic.ui.adapter.FragmentMainAdapter;
import com.ximcomputerx.formusic.ui.fragment.MixFragment;
import com.ximcomputerx.formusic.ui.fragment.MyFragment;
import com.ximcomputerx.formusic.ui.fragment.PlayFragment;
import com.ximcomputerx.formusic.ui.fragment.RankFragment;
import com.ximcomputerx.formusic.ui.fragment.SingerFragment;
import com.ximcomputerx.formusic.utils.ActivityManagerUtil;
import com.ximcomputerx.formusic.utils.DoubleClickExitUtil;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.view.PlayListDialog;

import org.litepal.LitePal;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @AUTHOR HACKER
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, DrawerLayout.DrawerListener {
    public static int DIALOG_MAIN = 0;
    public static int DIALOG_PLAY = 1;

    @Bind(R.id.view)
    protected View view;
    @Bind(R.id.dl_container)
    protected DrawerLayout dl_container;
    @Bind(R.id.navigation_view)
    protected NavigationView navigation_view;
    @Bind(R.id.tv_rank_music)
    protected TextView tv_rank_music;
    @Bind(R.id.tv_list_music)
    protected TextView tv_list_music;
    @Bind(R.id.tv_my_music)
    protected TextView tv_my_music;
    @Bind(R.id.tv_singer_list)
    protected TextView tv_singer_list;
    @Bind(R.id.viewpager)
    protected ViewPager viewPager;
    @Bind(R.id.iv_play_bar_playlist)
    protected ImageView iv_play_bar_playlist;
    @Bind(R.id.iv_play_bar_play)
    protected ImageView iv_play_bar_play;
    @Bind(R.id.fl_play_bar)
    protected FrameLayout fl_play_bar;
    @Bind(R.id.ll_container)
    protected LinearLayout ll_container;

    @Bind(R.id.ll_exit)
    protected LinearLayout ll_exit;
    @Bind(R.id.iv_icon)
    protected ImageView iv_icon;
    @Bind(R.id.iv_icon_background)
    protected ImageView iv_icon_background;

    private RankFragment rankFragment;
    private MixFragment listFragment;
    private MyFragment myFragment;
    private SingerFragment singerFragment;

    private DoubleClickExitUtil doubleClickExitUtil;

    private boolean isPlayFragmentShow;
    private PlayFragment mPlayFragment;

    private ControlPanel controlPanel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarView(view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                //.fitsSystemWindows(true)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();

        tv_rank_music.setSelected(true);
        tv_rank_music.setTextSize(22);

        dl_container.setDrawerListener(this);

        rankFragment = new RankFragment();
        listFragment = new MixFragment();
        singerFragment = new SingerFragment();
        myFragment = new MyFragment();
        FragmentMainAdapter adapter = new FragmentMainAdapter(getSupportFragmentManager());
        adapter.addFragment(rankFragment);
        adapter.addFragment(listFragment);
        adapter.addFragment(singerFragment);
        adapter.addFragment(myFragment);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        controlPanel = new ControlPanel(this, fl_play_bar);
        PlayManager.getInstance().addOnPlayEventListener(controlPanel);
    }

    @Override
    protected void initData() {
        doubleClickExitUtil = new DoubleClickExitUtil();
        GlideImageLoaderUtil.displayRoundImage(R.mipmap.ic_launcher, iv_icon, R.mipmap.ic_launcher);
        /*Glide.with(this).load(R.mipmap.ic_launcher)
                .dontAnimate()
                //.placeholder(Color.GRAY)
                //.error(Color.GRAY)
                .transform(new BlurTransformation(25, 3))
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(iv_icon_background);*/
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        PlayManager.getInstance().removeOnPlayEventListener(controlPanel);
        super.onDestroy();
    }

    @OnClick({R.id.tv_rank_music, R.id.tv_list_music, R.id.tv_my_music, R.id.tv_singer_list, R.id.iv_menu, R.id.iv_search, R.id.fl_play_bar, R.id.ll_exit,
            R.id.ll_about, R.id.ll_issues, R.id.ll_download})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_rank_music:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_list_music:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_singer_list:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_my_music:
                viewPager.setCurrentItem(3);
                break;
            case R.id.iv_menu:
                dl_container.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_search:
                intent = new Intent(this, SearchActivity.class);
                this.startActivity(intent);
                break;
            case R.id.fl_play_bar:
                showPlayingFragment();
                break;
            case R.id.ll_exit:
                closeDrawer();
                exit();
                break;
            case R.id.ll_about:
                closeDrawer();
                intent = new Intent(this, AboutActivity.class);
                this.startActivity(intent);
                break;
            case R.id.ll_issues:
                closeDrawer();
                intent = new Intent(this, IssuesActivity.class);
                this.startActivity(intent);
                break;
            case R.id.ll_download:
                closeDrawer();
                intent = new Intent(this, ScanActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    private void closeDrawer() {
        if (dl_container.isDrawerOpen(GravityCompat.START)) {
            dl_container.closeDrawers();
        }
    }

    /**
     * 播放事件统一处理
     * @param messageEvent
     */
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessageId()) {
            case EventId.EVENT_ID_MUSIC_TIME:
                controlPanel.getmProgressBar().setMax((int) PlayManager.getInstance().getPlayMusic().getDuration());
                break;
            case EventId.EVENT_ID_MUSIC_LIST:
                //PlayManager.getInstance().setMusicList((int) messageEvent.getMessageContent());
                //clearListType();
                break;
            case EventId.EVENT_ID_MUSIC_POSITION:
                PlayManager.getInstance().stopPlayer();
                PlayManager.getInstance().setPlayPosition((int) messageEvent.getMessageContent());
                PlayManager.getInstance().playPause();
                break;
            case EventId.EVENT_ID_MUSIC_HISTORY:
                MusicInfo musicInfo = PlayManager.getInstance().getPlayMusic();

                HistoryMusicInfo historyMusicInfo = new HistoryMusicInfo();

                toMusic(historyMusicInfo, musicInfo);

                LitePal.deleteAll(HistoryMusicInfo.class, "songId=?", historyMusicInfo.getSongId() + "");
                historyMusicInfo.save();

                break;
            case EventId.EVENT_ID_MUSIC_LIKE:
                MusicInfo musicInfoTemp = PlayManager.getInstance().getPlayMusic();

                LikeMusicInfo likeMusicInfo = new LikeMusicInfo();
                toMusic(likeMusicInfo, musicInfoTemp);

                List<LikeMusicInfo> likeMusicInfos = LitePal.where("songId=?", likeMusicInfo.getSongId() + "").find(LikeMusicInfo.class);
                if (likeMusicInfos != null && likeMusicInfos.size() > 1) {
                    LitePal.deleteAll(LikeMusicInfo.class, "songId=?", likeMusicInfo.getSongId() + "");
                } else {
                    likeMusicInfo.save();
                }
                break;
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
    }

    private void showPlayingFragment() {
        if (isPlayFragmentShow) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fragment_slide_up, 0);
        if (mPlayFragment == null) {
            mPlayFragment = new PlayFragment();
            ft.replace(android.R.id.content, mPlayFragment);
        } else {
            ft.show(mPlayFragment);
        }
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = true;

        ImmersionBar.with(this).reset();
        ImmersionBar.with(this).navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();
    }

    private void hidePlayingFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(0, R.anim.fragment_slide_down);
        ft.hide(mPlayFragment);
        ft.commitAllowingStateLoss();
        isPlayFragmentShow = false;

        ImmersionBar.with(this).reset();
        ImmersionBar.with(this).statusBarColor(R.color.white)
                .statusBarDarkFont(true).statusBarView(view)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                tv_rank_music.setSelected(true);
                tv_list_music.setSelected(false);
                tv_singer_list.setSelected(false);
                tv_my_music.setSelected(false);

                tv_rank_music.setTextSize(22);
                tv_list_music.setTextSize(15);
                tv_singer_list.setTextSize(15);
                tv_my_music.setTextSize(15);
                break;
            case 1:
                tv_rank_music.setSelected(false);
                tv_list_music.setSelected(true);
                tv_my_music.setSelected(false);
                tv_singer_list.setSelected(false);

                tv_rank_music.setTextSize(15);
                tv_list_music.setTextSize(22);
                tv_singer_list.setTextSize(15);
                tv_my_music.setTextSize(15);
                break;
            case 2:
                tv_rank_music.setSelected(false);
                tv_list_music.setSelected(false);
                tv_singer_list.setSelected(true);
                tv_my_music.setSelected(false);

                tv_rank_music.setTextSize(15);
                tv_list_music.setTextSize(15);
                tv_singer_list.setTextSize(22);
                tv_my_music.setTextSize(15);
                break;
            case 3:
                tv_rank_music.setSelected(false);
                tv_list_music.setSelected(false);
                tv_singer_list.setSelected(false);
                tv_my_music.setSelected(true);

                tv_rank_music.setTextSize(15);
                tv_list_music.setTextSize(15);
                tv_singer_list.setTextSize(15);
                tv_my_music.setTextSize(22);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private boolean slide;

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        if (!slide) {
            ImmersionBar.with(this).reset();
            ImmersionBar.with(this)
                    .statusBarView(view)
                    .statusBarColor(R.color.transparent)
                    .statusBarDarkFont(true)
                    //.fitsSystemWindows(true)
                    .navigationBarColor(R.color.white)
                    .navigationBarDarkIcon(true).init();
            slide = true;
        }
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        ImmersionBar.with(this)
                .statusBarView(view)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                //.fitsSystemWindows(true)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();
        slide = false;
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onBackPressed() {
        if (mPlayFragment != null && isPlayFragmentShow) {
            hidePlayingFragment();
            return;
        }
        if (dl_container.isDrawerOpen(GravityCompat.START)) {
            dl_container.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    public void playList(int type) {
        if (type == DIALOG_MAIN) {
            PlayListDialog playListDialog = new PlayListDialog(this);
            playListDialog.setGravity(Gravity.BOTTOM);
            playListDialog.show();
            playListDialog.setFullScreen(true);
        }
        if (type == DIALOG_PLAY) {
            PlayListDialog playListDialog = new PlayListDialog(this);
            playListDialog.setGravity(Gravity.BOTTOM);
            playListDialog.show();
            playListDialog.setFullScreen(true);
        }
    }

    private void exit() {
        ActivityManagerUtil.create().AppExit();
    }

}