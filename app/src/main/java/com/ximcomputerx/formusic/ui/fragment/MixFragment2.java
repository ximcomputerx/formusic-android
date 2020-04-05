package com.ximcomputerx.formusic.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseFragment;
import com.ximcomputerx.formusic.model.MixInfo;
import com.ximcomputerx.formusic.model.MixListInfo;
import com.ximcomputerx.formusic.ui.activity.SongListActivity;
import com.ximcomputerx.formusic.ui.adapter.ListMixAdapter;
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
public class MixFragment2 extends BaseFragment  implements SwipeRecyclerView.LoadMoreListener {
    @Bind(R.id.rv_mix_2)
    protected SwipeRecyclerView rv_mix_2;

    private int pageIndex = 1;
    private int pageNumer = 30;
    private int pageoffset = 0;

    private ListMixAdapter listMixAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mix_2;
    }

    @Override
    protected void initView(View contentView) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rv_mix_2.setLayoutManager(layoutManager);
        CustomLoadMoreView customLoadMoreView = new CustomLoadMoreView(context);
        rv_mix_2.addFooterView(customLoadMoreView);
        rv_mix_2.setLoadMoreView(customLoadMoreView);
        rv_mix_2.setLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        initMixData(true);
    }

    private void initMixData(boolean show) {
        getApiWrapper(show).mix(pageNumer, "hot", "摇滚", pageoffset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MixListInfo<Object>>() {
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
                    public void onNext(MixListInfo<Object> mixListInfo) {
                        if (mixListInfo != null && mixListInfo.getPlaylists() != null) {
                            if (pageIndex == 1) {
                                listMixAdapter = new ListMixAdapter(context, mixListInfo.getPlaylists());
                                rv_mix_2.setAdapter(listMixAdapter);
                                listMixAdapter.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position, Object data) {
                                        Intent intent = new Intent(context, SongListActivity.class);
                                        intent.putExtra(SongListActivity.SONGINFO, (MixInfo) data);
                                        context.startActivity(intent);
                                    }
                                });
                            } else {
                                listMixAdapter.addItems(mixListInfo.getPlaylists());
                            }
                            pageoffset = pageIndex * pageNumer;
                            pageIndex++;
                            if (mixListInfo.getPlaylists().size() < pageNumer) {
                                rv_mix_2.loadMoreFinish(false, false);
                            } else {
                                rv_mix_2.loadMoreFinish(false, true);
                            }
                        } else {
                            if (pageIndex == 1) {
                                rv_mix_2.setAdapter(null);
                            }
                            // 数据完更多数据，一定要调用这个方法
                            // 第一个参数：表示此次数据是否为空
                            // 第二个参数：表示是否还有更多数据
                            rv_mix_2.loadMoreFinish(true, false);
                        }
                    }
                });
    }

    @Override
    public void onLoadMore() {
        initMixData(true);
    }
}
