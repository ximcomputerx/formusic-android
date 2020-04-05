package com.ximcomputerx.formusic.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseFragment;
import com.ximcomputerx.formusic.model.SingerInfo;
import com.ximcomputerx.formusic.model.SingerListInfo;
import com.ximcomputerx.formusic.ui.activity.SingerSongListActivity;
import com.ximcomputerx.formusic.ui.adapter.ListSingerAdapter;
import com.ximcomputerx.formusic.ui.adapter.ListSongAdapter;
import com.ximcomputerx.formusic.util.ToastUtil;
import com.ximcomputerx.formusic.view.CustomLoadMoreView;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR HACKER
 */
public class SingerFragment extends BaseFragment implements SwipeRecyclerView.LoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.srl_singer)
    protected SwipeRefreshLayout srl_singer;
    @Bind({R.id.rv_singer})
    protected SwipeRecyclerView rv_singer;

    private int pageIndex = 1;
    private int pageNumer = 20;
    private int pageoffset = 0;

    private ListSingerAdapter listSingerAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main_singer;
    }

    @Override
    protected void initView(View contentView) {
        srl_singer.setColorSchemeResources(R.color.app_basic);
        srl_singer.setOnRefreshListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rv_singer.setLayoutManager(layoutManager);
        CustomLoadMoreView customLoadMoreView = new CustomLoadMoreView(context);
        rv_singer.addFooterView(customLoadMoreView);
        rv_singer.setLoadMoreView(customLoadMoreView);
        rv_singer.setLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        initArtistData(true);
    }

    private void initArtistData(boolean show) {
        getApiWrapper(show).singerList(pageNumer, pageoffset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SingerListInfo>() {
                    @Override
                    public void onCompleted() {
                        closeNetDialog();
                        srl_singer.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeNetDialog();
                        ToastUtil.showShortToast(getResources().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(SingerListInfo artistListIinfo) {
                        if (artistListIinfo != null && artistListIinfo.getArtists() != null) {
                            if (pageIndex == 1) {
                                listSingerAdapter = new ListSingerAdapter(getContext(), artistListIinfo.getArtists());
                                listSingerAdapter.setHasStableIds(true);
                                rv_singer.setAdapter(listSingerAdapter);
                                listSingerAdapter.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, Object data) {
                                        startSongList((SingerInfo) data);
                                    }
                                });
                            } else {
                                listSingerAdapter.addItems(artistListIinfo.getArtists());
                            }
                            pageoffset = pageIndex * pageNumer;
                            pageIndex++;
                            if (artistListIinfo.getArtists().size() < pageNumer) {
                                rv_singer.loadMoreFinish(false, false);
                            } else {
                                rv_singer.loadMoreFinish(false, true);
                            }
                        } else {
                            if (pageIndex == 1) {
                                rv_singer.setAdapter(null);
                            }
                            // 数据完更多数据，一定要调用这个方法
                            // 第一个参数：表示此次数据是否为空
                            // 第二个参数：表示是否还有更多数据
                            rv_singer.loadMoreFinish(true, false);
                        }
                    }
                });
    }

    private void startSongList(SingerInfo singerInfo) {
        Intent intent = new Intent(getContext(), SingerSongListActivity.class);
        intent.putExtra(SingerSongListActivity.SONGINFO, singerInfo);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        pageIndex = 1;
        pageoffset = 0;
        initArtistData(false);
    }

    @Override
    public void onLoadMore() {
        initArtistData(true);
    }
}