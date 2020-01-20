package com.ximcomputerx.formusic.ui.activity.my;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.base.BaseActivity;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.LikeMusicInfo;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.adapter.ListLikeAdapter;
import com.ximcomputerx.formusic.utils.SharedPreferencesUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LikeActivity extends BaseActivity {

    @Bind(R.id.rv_like)
    protected RecyclerView rv_like;
    @Bind(R.id.ll_play_all)
    protected LinearLayout ll_play_all;
    @Bind(R.id.tv_number)
    protected TextView tv_number;

    private List<LikeMusicInfo> likeMusicInfos;

    private int position;
    private boolean addFlag;

    private ListLikeAdapter listLikeAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_like;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_like.setLayoutManager(mLayoutManager);
    }

    @OnClick({R.id.iv_back, R.id.ll_play_all})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_play_all:
                LikeActivity.this.position = 0;
                playList();
                addFlag = true;
                listLikeAdapter.setPlaySongId(null);
                listLikeAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void initData() {
        String like = SharedPreferencesUtil.getStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_LIKE, "");
        if (like.equals(Constant.PLAY_LIST_LIKE)) {
            addFlag = true;
        }
        initHistoryData();
    }

    private void initHistoryData() {
        likeMusicInfos = LitePal.findAll(LikeMusicInfo.class);

        if (likeMusicInfos != null && likeMusicInfos.size() > 0) {
            ll_play_all.setVisibility(View.VISIBLE);
            tv_number.setText("(共" + likeMusicInfos.size() + "首)");
        }

        listLikeAdapter = new ListLikeAdapter(this, likeMusicInfos);
        rv_like.setAdapter(listLikeAdapter);
        listLikeAdapter.setOnItemClickListener(new ListLikeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                if (!addFlag) {
                    LikeActivity.this.position = 0;
                    LikeActivity.this.position = position;
                    playList();
                    addFlag = true;
                } else {
                    // 发送位置更新事件
                    EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_POSITION, position));
                }
                listLikeAdapter.setPlaySongId(((LikeMusicInfo) data).getSongId() + "");
                listLikeAdapter.notifyDataSetChanged();
            }
        });
    }

    private void playList() {
        LitePal.deleteAll(MusicInfo.class, "");
        List<MusicInfo> musicInfos = new ArrayList<>();
        for (int i = 0; i < likeMusicInfos.size(); i++) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setType(likeMusicInfos.get(i).getType());
            musicInfo.setTitle(likeMusicInfos.get(i).getTitle());
            musicInfo.setSongId(likeMusicInfos.get(i).getSongId());
            musicInfo.setArtist(likeMusicInfos.get(i).getArtist());
            musicInfo.setCoverPath(likeMusicInfos.get(i).getCoverPath());
            musicInfo.setPath(likeMusicInfos.get(i).getPath());
            musicInfos.add(musicInfo);
        }

        // 保存到播放列表
        for (MusicInfo musicInfo : musicInfos) {
            musicInfo.save();
        }

        PlayManager.getInstance().setMusicList(musicInfos, position);
        // 发送播放列表更新事件
        clearListType();
        // 保存播放列表标记
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_LIKE, Constant.PLAY_LIST_LIKE);
    }

    private void clearListType() {
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SINGER, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SONG, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SEARCH, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_HISTORY, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_LIKE, "");
    }

}
