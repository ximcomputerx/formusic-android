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
import com.ximcomputerx.formusic.model.HistoryMusicInfo;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.adapter.ListHistoryAdapter;
import com.ximcomputerx.formusic.utils.SharedPreferencesUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import es.dmoral.toasty.Toasty;

public class HistoryActivity extends BaseActivity {
    @Bind(R.id.rv_history)
    protected RecyclerView rv_history;
    @Bind(R.id.ll_play_all)
    protected LinearLayout ll_play_all;
    @Bind(R.id.tv_number)
    protected TextView tv_number;

    private List<HistoryMusicInfo> historyMusicInfos;
    private int position;
    private boolean addFlag;

    private ListHistoryAdapter listHistoryAdapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .navigationBarColor(R.color.white)
                .navigationBarDarkIcon(true).init();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_history.setLayoutManager(mLayoutManager);
    }

    @OnClick({R.id.iv_back, R.id.iv_clear, R.id.ll_play_all})
    protected void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_clear:
                LitePal.deleteAll(HistoryMusicInfo.class, "");
                Toasty.normal(getApplicationContext(), "清空成功", Toasty.LENGTH_SHORT).show();
                listHistoryAdapter.getDataList().clear();
                listHistoryAdapter.notifyDataSetChanged();
                break;
            case R.id.ll_play_all:
                HistoryActivity.this.position = 0;
                playList();
                addFlag = true;
                listHistoryAdapter.setPlaySongId(null);
                listHistoryAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void initData() {
        String history = SharedPreferencesUtil.getStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_HISTORY, "");
        if (history.equals(Constant.PLAY_LIST_HISTORY)) {
            addFlag = true;
        }
        initHistoryData();
    }

    private void initHistoryData() {
        historyMusicInfos = LitePal.findAll(HistoryMusicInfo.class);
        if (historyMusicInfos != null && historyMusicInfos.size() > 0) {
            ll_play_all.setVisibility(View.VISIBLE);
            tv_number.setText("(共" + historyMusicInfos.size() + "首)");
        }

        listHistoryAdapter = new ListHistoryAdapter(this, historyMusicInfos);
        rv_history.setAdapter(listHistoryAdapter);
        listHistoryAdapter.setOnItemClickListener(new ListHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                if (!addFlag) {
                    HistoryActivity.this.position = 0;
                    HistoryActivity.this.position = position;
                    playList();
                    addFlag = true;
                } else {
                    // 发送位置更新事件
                    EventBus.getDefault().post(new MessageEvent(EventId.EVENT_ID_MUSIC_POSITION, position));
                }
                listHistoryAdapter.setPlaySongId(((HistoryMusicInfo) data).getSongId() + "");
                listHistoryAdapter.notifyDataSetChanged();
            }
        });
    }

    private void playList() {
        LitePal.deleteAll(MusicInfo.class, "");
        List<MusicInfo> musicInfos = new ArrayList<>();
        for (int i = 0; i < historyMusicInfos.size(); i++) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.setType(historyMusicInfos.get(i).getType());
            musicInfo.setTitle(historyMusicInfos.get(i).getTitle());
            musicInfo.setSongId(historyMusicInfos.get(i).getSongId());
            musicInfo.setArtist(historyMusicInfos.get(i).getArtist());
            musicInfo.setCoverPath(historyMusicInfos.get(i).getCoverPath());
            musicInfo.setPath(historyMusicInfos.get(i).getPath());
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
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SONG, Constant.PLAY_LIST_SONG);
    }

    private void clearListType() {
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SINGER, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SONG, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_SEARCH, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_HISTORY, "");
        SharedPreferencesUtil.setStringPreferences(Constant.PREFERENCES, Constant.PLAY_LIST_LIKE, "");
    }

}
