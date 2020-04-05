package com.ximcomputerx.formusic.ui.fragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseFragment;
import com.ximcomputerx.formusic.model.MixInfo;
import com.ximcomputerx.formusic.model.RecommendIinfo;
import com.ximcomputerx.formusic.model.RecommendListIinfo;
import com.ximcomputerx.formusic.ui.activity.HistoryActivity;
import com.ximcomputerx.formusic.ui.activity.LikeActivity;
import com.ximcomputerx.formusic.ui.activity.SongListActivity;
import com.ximcomputerx.formusic.ui.adapter.ListRecommendAdapter;
import com.ximcomputerx.formusic.util.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @AUTHOR HACKER
 */
public class MyFragment extends BaseFragment {
    @Bind({R.id.rv_recommend})
    protected RecyclerView rv_recommend;

    private ListRecommendAdapter listRecommendAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_main_my;
    }

    @Override
    protected void initView(View contentView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_recommend.setLayoutManager(linearLayoutManager);
        rv_recommend.setNestedScrollingEnabled(false);
        rv_recommend.setHasFixedSize(false);
    }

    @Override
    protected void initData() {
        initRecommendData();
    }

    @OnClick({R.id.ll_local, R.id.ll_recent, R.id.ll_like, R.id.ll_download})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_local:

                break;
            case R.id.ll_recent:
                Intent intentRecent = new Intent(getContext(), HistoryActivity.class);
                context.startActivity(intentRecent);
                break;
            case R.id.ll_like:
                Intent intentLike = new Intent(getContext(), LikeActivity.class);
                context.startActivity(intentLike);
                break;
            case R.id.ll_download:
                break;
        }
    }

    private void initRecommendData() {
        getApiWrapper(true).recommendList("30")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendListIinfo>() {
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
                    public void onNext(RecommendListIinfo recommendListIinfo) {
                        if (recommendListIinfo != null) {
                            listRecommendAdapter = new ListRecommendAdapter(getContext(), recommendListIinfo.getResult());
                            rv_recommend.setAdapter(listRecommendAdapter);
                            listRecommendAdapter.setOnItemClickListener(new ListRecommendAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position, Object data) {
                                    startSongList(((RecommendIinfo) data));
                                }
                            });
                        }
                    }
                });
    }

    private void startSongList(RecommendIinfo recommendIinfo) {
        Intent intent = new Intent(getContext(), SongListActivity.class);
        MixInfo mixInfo = new MixInfo();
        mixInfo.setId(recommendIinfo.getId());
        mixInfo.setName(recommendIinfo.getName());
        mixInfo.setDescription(recommendIinfo.getCopywriter());
        mixInfo.setCoverImgUrl(recommendIinfo.getPicUrl());
        intent.putExtra(SongListActivity.SONGINFO, mixInfo);
        context.startActivity(intent);
    }
}
