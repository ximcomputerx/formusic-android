package com.ximcomputerx.formusic.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.anzewei.parallaxbacklayout.ParallaxBack;
import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.model.SearchHotInfo;
import com.ximcomputerx.formusic.model.SearchHotListInfo;
import com.ximcomputerx.formusic.model.SearchInfo;
import com.ximcomputerx.formusic.model.SearchListInfo;
import com.ximcomputerx.formusic.model.SongUrlInfo;
import com.ximcomputerx.formusic.model.SongUrlListInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.adapter.ListSearchDetailAdapter;
import com.ximcomputerx.formusic.ui.adapter.ListSearchHotAdapter;
import com.ximcomputerx.formusic.util.SharedPreferencesUtil;
import com.ximcomputerx.formusic.util.ToastUtil;
import com.ximcomputerx.formusic.view.CustomLoadMoreView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@ParallaxBack(edge = ParallaxBack.Edge.LEFT,layout = ParallaxBack.Layout.COVER)
public class SearchActivity extends BaseActivity implements SwipeRecyclerView.LoadMoreListener {
    @Bind(R.id.rv_host)
    protected RecyclerView rv_host;
    @Bind(R.id.rv_search)
    protected SwipeRecyclerView rv_search;
    @Bind(R.id.tv_list_music)
    protected EditText tv_list_music;
    @Bind(R.id.ll_hot)
    protected LinearLayout ll_hot;
    @Bind(R.id.ll_search)
    protected LinearLayout ll_search;
    @Bind(R.id.iv_back)
    protected ImageView iv_back;

    private List<SearchInfo> searchInfos;
    private String ids = "";
    private boolean addFlag;
    private int position = 0;

    private int pageIndex = 1;
    private int pageNumer = 50;
    private int pageoffset = 0;

    private ListSearchHotAdapter listSearchHotAdapter;
    private ListSearchDetailAdapter listSearchDetailAdapter;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();

        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        rv_host.setLayoutManager(mLayoutManager1);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rv_search.setLayoutManager(mLayoutManager2);
        CustomLoadMoreView customLoadMoreView = new CustomLoadMoreView(context);
        rv_search.addFooterView(customLoadMoreView);
        rv_search.setLoadMoreView(customLoadMoreView);
        rv_search.setLoadMoreListener(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_search, R.id.ll_play_all})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                if(TextUtils.isEmpty(tv_list_music.getText().toString().trim())) {
                    return;
                }
                iniSerchDetailData(tv_list_music.getText().toString().trim());
                ll_hot.setVisibility(View.GONE);
                ll_search.setVisibility(View.VISIBLE);
                if (searchInfos != null) {
                    searchInfos.clear();
                }
                break;
            case R.id.ll_play_all:
                SearchActivity.this.position = 0;
                playList();
                addFlag = true;
                listSearchDetailAdapter.setPlaySongId(null);
                listSearchDetailAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void initData() {
        iniSerchHotData();
    }

    private void iniSerchHotData() {
        getApiWrapper(true).searchHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchHotListInfo>() {
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
                    public void onNext(SearchHotListInfo searchHotListInfo) {

                        if (searchHotListInfo != null) {
                            listSearchHotAdapter = new ListSearchHotAdapter(SearchActivity.this, searchHotListInfo.getData());
                            rv_host.setAdapter(listSearchHotAdapter);
                            listSearchHotAdapter.setOnItemClickListener(new ListSearchHotAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position, Object data) {
                                    tv_list_music.setText("");
                                    String key = ((SearchHotInfo) data).getSearchWord();
                                    tv_list_music.setText(key);
                                    iniSerchDetailData(key);

                                }
                            });
                        }
                    }
                });
    }

    private void iniSerchDetailData(String key) {
        ll_hot.setVisibility(View.GONE);
        ll_search.setVisibility(View.VISIBLE);
        getApiWrapper(true).searchList(key, pageNumer, pageoffset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchListInfo>() {
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
                    public void onNext(SearchListInfo searchListInfo) {
                        searchInfos = searchListInfo.getResult().getSongs();

                        if (searchListInfo != null && searchListInfo.getResult().getSongs() != null) {
                            searchInfos = searchListInfo.getResult().getSongs();
                            if (pageIndex == 1) {
                                listSearchDetailAdapter = new ListSearchDetailAdapter(SearchActivity.this, searchInfos);
                                listSearchDetailAdapter.setHasStableIds(true);
                                rv_search.setAdapter(listSearchDetailAdapter);
                                listSearchDetailAdapter.setOnItemClickListener(new ListSearchDetailAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, Object data) {
                                        if (!addFlag) {
                                            SearchActivity.this.position = 0;
                                            SearchActivity.this.position = position;
                                            playList();
                                            addFlag = true;
                                        } else {
                                            // 发送位置更新事件
                                            EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_POSITION, position));
                                        }
                                        listSearchDetailAdapter.setPlaySongId(((SearchInfo) data).getId());
                                        listSearchDetailAdapter.notifyDataSetChanged();
                                    }
                                });
                            } else {
                                listSearchDetailAdapter.addItems(searchInfos);
                            }
                            pageoffset = pageIndex * pageNumer;
                            pageIndex++;
                            if (searchInfos.size() < pageNumer) {
                                rv_search.loadMoreFinish(false, false);
                            } else {
                                rv_search.loadMoreFinish(false, true);
                            }
                        } else {
                            if (pageIndex == 1) {
                                rv_search.setAdapter(null);
                            }
                            // 数据完更多数据，一定要调用这个方法
                            // 第一个参数：表示此次数据是否为空
                            // 第二个参数：表示是否还有更多数据
                            rv_search.loadMoreFinish(true, false);
                        }
                    }

                });
    }

    @Override
    public void onLoadMore() {
        iniSerchDetailData(tv_list_music.getText().toString().trim());
    }

    private void playList() {
        if (searchInfos != null && searchInfos.size() > 0) {
            // 清空播放列表
            LitePal.deleteAll(MusicInfo.class, "");
            //Toasty.normal(getApplicationContext(), "清空成功", Toasty.LENGTH_SHORT).show();

            ids = "";
            List<MusicInfo> musicInfos = new ArrayList<>();
            for (int i = 0; i < searchInfos.size(); i++) {
                if (i == searchInfos.size() - 1) {
                    ids = ids + searchInfos.get(i).getId();
                } else {
                    ids = ids + searchInfos.get(i).getId() + ",";
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
                            for (SearchInfo searchInfo : searchInfos) {
                                for (SongUrlInfo songUrlInfo : songUrlInfos) {
                                    if (searchInfo.getId().equals(songUrlInfo.getId())) {
                                        searchInfo.setUrl(songUrlInfo.getUrl());
                                        searchInfo.setSize(songUrlInfo.getSize());
                                    }
                                }
                            }

                            List<MusicInfo> musicInfos = new ArrayList<>();
                            for (int i = 0; i < searchInfos.size(); i++) {
                                //if (!TextUtils.isEmpty(songInfos.get(i).getUrl())) {
                                MusicInfo musicInfo = new MusicInfo();
                                musicInfo.setType(MusicInfo.Type.ONLINE);
                                musicInfo.setTitle(searchInfos.get(i).getName());
                                musicInfo.setSongId(Long.parseLong(searchInfos.get(i).getId()));
                                musicInfo.setArtist(searchInfos.get(i).getArtists().get(0).getName());
                                musicInfo.setCoverPath(searchInfos.get(i).getArtists().get(0).getImg1v1Url());
                                musicInfo.setAlbum(searchInfos.get(i).getAlbum().getName());
                                //String path = "https://music.163.com/song/media/outer/url?id=" + Long.parseLong(searchInfos.get(i).getId()) + ".mp3";
                                musicInfo.setPath(searchInfos.get(i).getUrl());
                                //musicInfo.setPath(songInfos.get(i).getUrl());
                                musicInfos.add(musicInfo);
                                //}
                            }

                            // 保存到播放列表
                            for (MusicInfo musicInfo : musicInfos) {
                                musicInfo.save();
                            }

                            // 发送播放列表更新事件
                            // EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_LIST, position));
                            PlayManager.getInstance().setMusicList(musicInfos, position);
                            clearListType();
                            SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SEARCH, "SEARCH");
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
