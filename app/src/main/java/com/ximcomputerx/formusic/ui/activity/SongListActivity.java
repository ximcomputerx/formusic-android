package com.ximcomputerx.formusic.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.MixInfo;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.model.SongInfo;
import com.ximcomputerx.formusic.model.SongListInfo;
import com.ximcomputerx.formusic.model.SongUrlInfo;
import com.ximcomputerx.formusic.model.SongUrlListInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.adapter.ListSongAdapter;
import com.ximcomputerx.formusic.util.CommonUtils;
import com.ximcomputerx.formusic.util.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.util.SharedPreferencesUtil;
import com.ximcomputerx.formusic.util.StatusBarUtil;
import com.ximcomputerx.formusic.util.TextViewBinder;
import com.ximcomputerx.formusic.util.ToastUtil;
import com.ximcomputerx.formusic.view.CustomNestedScrollView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR HACKER
 */
@ParallaxBack(edge = ParallaxBack.Edge.LEFT, layout = ParallaxBack.Layout.COVER)
public class SongListActivity extends BaseActivity {
    public static String SONGINFO = "SONGINFO";

    //@Bind(R.id.iv_search)
    //protected ImageView iv_search;
    @Bind(R.id.rv_songlist)
    protected RecyclerView rv_songlist;
    @Bind(R.id.tv_name)
    protected TextView tv_name;
    @Bind(R.id.tv_artist)
    protected TextView tv_artist;
    @Bind(R.id.tv_des)
    protected TextView tv_des;

    @Bind(R.id.img_item_bg)
    protected ImageView img_item_bg;
    @Bind(R.id.iv_one_photo)
    protected ImageView iv_one_photo;
    //@Bind(R.id.title_tool_bar)
    //protected Toolbar title_tool_bar;
    @Bind(R.id.title_tool_bar)
    protected LinearLayout title_tool_bar;
    @Bind(R.id.tv_list_music)
    protected TextView tv_list_music;
    @Bind(R.id.iv_title_head_bg)
    protected ImageView iv_title_head_bg;
    @Bind(R.id.nsv_scrollview)
    protected CustomNestedScrollView nsv_scrollview;
    @Bind(R.id.ll_play_all)
    protected LinearLayout ll_play_all;
    @Bind(R.id.tv_number)
    protected TextView tv_number;
    // 这个是高斯图背景的高度
    private int imageBgHeight;
    // 在多大范围内变色
    private int slidingDistance;

    private MixInfo mixInfo;

    private int pageIndex = 1;
    private int pageNumer = 20;

    private ListSongAdapter listSongAdapter;

    private SongListInfo songListInfo;
    private List<SongInfo> songInfos;
    private String ids = "";

    private boolean addFlag;

    private int position = 0;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_songlist;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mixInfo = (MixInfo) intent.getSerializableExtra(SONGINFO);

        String mixId = SharedPreferencesUtil.getStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SONG, "");
        if (mixId.equals(mixInfo.getId())) {
            addFlag = true;
        }

        initHeadData();

        setPicture();
        setTitleBar();
        initSlideShapeTheme();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_songlist.setLayoutManager(mLayoutManager);
        // 需加，不然滑动不流畅
        rv_songlist.setNestedScrollingEnabled(false);
        rv_songlist.setHasFixedSize(false);
        initSongListData();

    }

    private void initHeadData() {
        TextViewBinder.setTextView(tv_name, mixInfo.getName());
        TextViewBinder.setTextView(tv_des, mixInfo.getDescription());
    }

    /**
     * 高斯背景图和一般图片
     */
    private void setPicture() {
        //GlideImageLoaderUtil.displayImage(mixInfo.getCoverImgUrl(), iv_one_photo);
        GlideImageLoaderUtil.displayRoundImage(mixInfo.getCoverImgUrl(), iv_one_photo, R.mipmap.default_cover);
        // "14":模糊度；"3":图片缩放3倍后再进行模糊
        Glide.with(this).load(mixInfo.getCoverImgUrl())
                .dontAnimate()
                //.placeholder(Color.GRAY)
                //.error(Color.GRAY)
                .transform(new BlurTransformation(25, 3))
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(img_item_bg);
    }

    private void setTitleBar() {
        tv_list_music.setText(mixInfo.getName());
        tv_list_music.setSelected(true);
        /*setSupportActionBar(title_tool_bar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_back);
        }
        title_tool_bar.setTitle(mixInfo.getName());
        title_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }

    /**
     * 初始化滑动渐变
     */
    private void initSlideShapeTheme() {
        setImgHeaderBg();

        // toolbar的高度
        int toolbarHeight = title_tool_bar.getLayoutParams().height;
        // toolbar+状态栏的高度
        final int headerBgHeight = toolbarHeight + StatusBarUtil.getStatusBarHeight(this);

        // 使背景图向上移动到图片的最底端，保留toolbar+状态栏的高度
        iv_title_head_bg.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = iv_title_head_bg.getLayoutParams();
        ViewGroup.MarginLayoutParams ivTitleHeadBgParams = (ViewGroup.MarginLayoutParams) iv_title_head_bg.getLayoutParams();
        int marginTop = params.height - headerBgHeight;
        ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0);
        iv_title_head_bg.setImageAlpha(0);

        // 为头部是View的界面设置状态栏透明
        StatusBarUtil.setTranslucentImageHeader(this, 0, title_tool_bar);

        ViewGroup.LayoutParams imgItemBgparams = img_item_bg.getLayoutParams();
        // 获得高斯图背景的高度
        imageBgHeight = imgItemBgparams.height;

        // 监听改变透明度
        initScrollViewListener();
    }

    /**
     * 加载titlebar背景,加载后将背景设为透明
     */
    private void setImgHeaderBg() {
        Glide.with(this).load(mixInfo.getCoverImgUrl())
//                .placeholder(R.drawable.stackblur_default)
                .transform(new BlurTransformation(40, 8))// 设置高斯模糊
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        title_tool_bar.setBackgroundColor(Color.TRANSPARENT);
                        iv_title_head_bg.setImageAlpha(0);
                        iv_title_head_bg.setVisibility(View.VISIBLE);
                        return false;
                    }
                }).into(iv_title_head_bg);
    }


    private void initScrollViewListener() {
        // 为了兼容api23以下
        nsv_scrollview.setOnMyScrollChangeListener(new CustomNestedScrollView.ScrollInterface() {
            @Override
            public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                scrollChangeHeader(scrollY);
            }
        });

        int titleBarAndStatusHeight = (int) (CommonUtils.getDimens(R.dimen.nav_bar_height)) + StatusBarUtil.getStatusBarHeight(this);
        slidingDistance = imageBgHeight - titleBarAndStatusHeight - (int) (CommonUtils.getDimens(R.dimen.nav_bar_height_more));
    }

    private void scrollChangeHeader(int scrolledY) {

//        DebugUtil.error("---scrolledY:  " + scrolledY);
//        DebugUtil.error("-----slidingDistance: " + slidingDistance);

        if (scrolledY < 0) {
            scrolledY = 0;
        }
        float alpha = Math.abs(scrolledY) * 1.0f / (slidingDistance);
        Drawable drawable = iv_title_head_bg.getDrawable();
//        DebugUtil.error("----alpha:  " + alpha);

        if (drawable != null) {
            if (scrolledY <= slidingDistance) {
                // title部分的渐变
                drawable.mutate().setAlpha((int) (alpha * 255));
                iv_title_head_bg.setImageDrawable(drawable);
            } else {
                drawable.mutate().setAlpha(255);
                iv_title_head_bg.setImageDrawable(drawable);
            }
        }
    }

    @OnClick({R.id.ll_play_all, R.id.iv_back})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_play_all:
                SongListActivity.this.position = 0;
                playList();
                addFlag = true;
                listSongAdapter.setPlaySongId(null);
                listSongAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void initSongListData() {
        getApiWrapper(true).songList(mixInfo.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SongListInfo<Object>>() {
                    @Override
                    public void onCompleted() {
                        closeNetDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeNetDialog();
                        ToastUtil.showShortToast(getResources().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(SongListInfo<Object> songListInfo) {

                        if (songListInfo != null) {
                            if (songListInfo.getPlaylist().getTracks() != null) {
                                SongListActivity.this.songListInfo = songListInfo;
                                songInfos = songListInfo.getPlaylist().getTracks();

                                listSongAdapter = new ListSongAdapter(context, songInfos);
                                rv_songlist.setAdapter(listSongAdapter);
                                ll_play_all.setVisibility(View.VISIBLE);
                                tv_number.setText("(共" + songInfos.size() + "首)");

                                listSongAdapter.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, Object data) {
                                        if (!addFlag) {
                                            SongListActivity.this.position = 0;
                                            SongListActivity.this.position = position;
                                            playList();
                                            addFlag = true;
                                        } else {
                                            // 发送位置更新事件
                                            EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_POSITION, position));
                                        }
                                        listSongAdapter.setPlaySongId(((SongInfo) data).getId());
                                        listSongAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });
    }

    private void playList() {
        if (songInfos != null && songInfos.size() > 0) {
            // 清空播放列表
            LitePal.deleteAll(MusicInfo.class, "");
            //Toasty.normal(getApplicationContext(), "清空成功", Toasty.LENGTH_SHORT).show();

            ids = "";
            List<MusicInfo> musicInfos = new ArrayList<>();
            for (int i = 0; i < songInfos.size(); i++) {
                if (i == songInfos.size() - 1) {
                    ids = ids + songInfos.get(i).getId();
                } else {
                    ids = ids + songInfos.get(i).getId() + ",";
                }
            }
            initSongUrlListData();
        }
    }


    private void initSongUrlListData() {
        getApiWrapper(true).songUrlList(ids)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SongUrlListInfo<SongUrlInfo>>() {
                    @Override
                    public void onCompleted() {
                        closeNetDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeNetDialog();
                        ToastUtil.showShortToast(getResources().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(SongUrlListInfo<SongUrlInfo> songUrlListInfo) {

                        if (songUrlListInfo != null) {
                            List<SongUrlInfo> songUrlInfos = songUrlListInfo.getData();
                            for (SongInfo songInfo : songInfos) {
                                for (SongUrlInfo songUrlInfo : songUrlInfos) {
                                    if (songInfo.getId().equals(songUrlInfo.getId())) {
                                        songInfo.setUrl(songUrlInfo.getUrl());
                                        songInfo.setSize(songUrlInfo.getSize());
                                    }
                                }
                            }

                            List<MusicInfo> musicInfos = new ArrayList<>();
                            for (int i = 0; i < songInfos.size(); i++) {
                                //if (!TextUtils.isEmpty(songInfos.get(i).getUrl())) {
                                MusicInfo musicInfo = new MusicInfo();
                                musicInfo.setType(MusicInfo.Type.ONLINE);
                                musicInfo.setTitle(songInfos.get(i).getName());
                                musicInfo.setSongId(Long.parseLong(songInfos.get(i).getId()));
                                musicInfo.setArtist(songInfos.get(i).getAr().get(0).getName());
                                musicInfo.setCoverPath(songInfos.get(i).getAl().getPicUrl());
                                musicInfo.setAlbum(songInfos.get(i).getAl().getName());
                                String path = "https://music.163.com/song/media/outer/url?id=" + Long.parseLong(songInfos.get(i).getId()) + ".mp3";
                                musicInfo.setPath(path);
                                //musicInfo.setPath(songInfos.get(i).getUrl());
                                musicInfos.add(musicInfo);
                                //}
                            }

                            // 保存到播放列表
                            for (MusicInfo musicInfo : musicInfos) {
                                musicInfo.save();
                            }
                            PlayManager.getInstance().setMusicList(musicInfos, position);
                            // 发送播放列表更新事件
                            clearListType();
                            // 保存播放列表标记
                            SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SONG, mixInfo.getId());
                            //Toasty.normal(getApplicationContext(), "添加列表成功", Toasty.LENGTH_SHORT).show();
                        } else {

                        }
                    }
                });
    }

    private void clearListType() {
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SINGER, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SONG, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SEARCH, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_HISTORY, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_LIKE, "");
    }
}
