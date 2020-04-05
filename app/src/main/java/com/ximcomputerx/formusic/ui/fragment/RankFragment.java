package com.ximcomputerx.formusic.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseFragment;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.model.MixInfo;
import com.ximcomputerx.formusic.model.RankInfo;
import com.ximcomputerx.formusic.model.RankListInfo;
import com.ximcomputerx.formusic.ui.activity.SongListActivity;
import com.ximcomputerx.formusic.ui.adapter.ListRankAdapter;
import com.ximcomputerx.formusic.ui.adapter.ListSongAdapter;
import com.ximcomputerx.formusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @AUTHOR HACKER
 */
public class RankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.srl_rank)
    protected SwipeRefreshLayout srl_rank;
    @Bind(R.id.rv_1)
    protected RecyclerView rv_official;
    @Bind(R.id.rv_2)
    protected RecyclerView rv_recommend;
    @Bind(R.id.rv_3)
    protected RecyclerView rv_world;
    @Bind(R.id.rv_4)
    protected RecyclerView rv_other;

    private List<RankInfo> official;
    private List<RankInfo> recommend;
    private List<RankInfo> world;
    private List<RankInfo> other;

    private ListRankAdapter listRankAdapter1;
    private ListRankAdapter listRankAdapter2;
    private ListRankAdapter listRankAdapter3;
    private ListRankAdapter listRankAdapter4;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main_rank;
    }

    @Override
    protected void initView(View contentView) {
        srl_rank.setColorSchemeResources(R.color.app_basic);
        srl_rank.setOnRefreshListener(this);

        GridLayoutManager layoutManager1 = new GridLayoutManager(getContext(), 3);
        rv_official.setLayoutManager(layoutManager1);
        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 3);
        rv_recommend.setLayoutManager(layoutManager2);
        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 3);
        rv_world.setLayoutManager(layoutManager3);
        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(), 3);
        rv_other.setLayoutManager(layoutManager4);
        rv_official.setNestedScrollingEnabled(false);
        rv_official.setHasFixedSize(false);
        rv_recommend.setNestedScrollingEnabled(false);
        rv_recommend.setHasFixedSize(false);
        rv_world.setNestedScrollingEnabled(false);
        rv_world.setHasFixedSize(false);
        rv_other.setNestedScrollingEnabled(false);
        rv_other.setHasFixedSize(false);
    }

    @Override
    protected void initData() {
        initRandData();
    }

    private void initRandData() {
        getApiWrapper(true).rank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RankListInfo<Object>>() {
                    @Override
                    public void onCompleted() {
                        closeNetDialog();
                        srl_rank.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeNetDialog();
                        ToastUtil.showShortToast(getResources().getString(R.string.load_error));
                    }

                    @Override
                    public void onNext(RankListInfo<Object> rankListInfo) {
                        if (rankListInfo != null) {
                            List<RankInfo> rankInfos = rankListInfo.getList();
                            official = getofficial(rankInfos);
                            recommend = getRecommend(rankInfos);
                            world = getWorld(rankInfos);
                            other = getOther(rankInfos);
                            initAdapter();
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        initRandData();
    }

    private void initAdapter() {
        listRankAdapter1 = new ListRankAdapter(getContext(), official);
        listRankAdapter2 = new ListRankAdapter(getContext(), recommend);
        listRankAdapter3 = new ListRankAdapter(getContext(), world);
        listRankAdapter4 = new ListRankAdapter(getContext(), other);
        rv_official.setAdapter(listRankAdapter1);
        rv_recommend.setAdapter(listRankAdapter2);
        rv_world.setAdapter(listRankAdapter3);
        rv_other.setAdapter(listRankAdapter4);

        listRankAdapter1.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                startSongList((RankInfo) data);
            }
        });
        listRankAdapter2.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                startSongList((RankInfo) data);
            }
        });
        listRankAdapter3.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                startSongList((RankInfo) data);
            }
        });
        listRankAdapter4.setOnItemClickListener(new ListSongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                startSongList((RankInfo) data);
            }
        });
    }

    private void startSongList(RankInfo rankInfo) {
        Intent intent = new Intent(getContext(), SongListActivity.class);
        MixInfo mixInfo = new MixInfo();
        mixInfo.setId(rankInfo.getId());
        mixInfo.setName(rankInfo.getName());
        mixInfo.setDescription(rankInfo.getDescription());
        mixInfo.setCoverImgUrl(rankInfo.getCoverImgUrl());
        intent.putExtra(SongListActivity.SONGINFO, mixInfo);
        context.startActivity(intent);
    }

    public List<RankInfo> getofficial(List<RankInfo> rankInfos) {
        List<RankInfo> official = new ArrayList<>();
        for (int i = 0; i < rankInfos.size(); i++) {
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_official_1)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_official);
                official.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_official_2)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_official);
                official.add(rankInfos.get(i));

            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_official_3)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_official);
                official.add(rankInfos.get(i));

            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_official_4)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_official);
                official.add(rankInfos.get(i));

            }
        }
        return official;
    }

    public List<RankInfo> getRecommend(List<RankInfo> rankInfos) {
        List<RankInfo> recommend = new ArrayList<>();
        for (int i = 0; i < rankInfos.size(); i++) {
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_recommend_1)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_recommend);
                recommend.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_recommend_2)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_recommend);
                recommend.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_recommend_3)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_recommend);
                recommend.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_recommend_4)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_recommend);
                recommend.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_recommend_5)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_recommend);
                recommend.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_recommend_6)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_recommend);
                recommend.add(rankInfos.get(i));
            }
        }
        return recommend;
    }

    public List<RankInfo> getWorld(List<RankInfo> rankInfos) {
        List<RankInfo> world = new ArrayList<>();
        for (int i = 0; i < rankInfos.size(); i++) {
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_world_1)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_world);
                world.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_world_2)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_world);
                world.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_world_3)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_world);
                world.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_world_4)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_world);
                world.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_world_5)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_world);
                world.add(rankInfos.get(i));
            }
            if (rankInfos.get(i).getName().equals(Constant.RANK_TYPE_world_6)) {
                rankInfos.get(i).setType(Constant.RANK_TYPE_world);
                world.add(rankInfos.get(i));
            }
        }
        return world;
    }

    public List<RankInfo> getOther(List<RankInfo> rankInfos) {
        List<RankInfo> other = new ArrayList<>();
        for (RankInfo rankInfo : rankInfos) {
            if (rankInfo.getType() == Constant.RANK_TYPE_other) {
                other.add(rankInfo);
            }
        }
        return other;
    }
}
