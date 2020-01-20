package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.config.Constant;
import com.ximcomputerx.formusic.enums.PlayModeEnum;
import com.ximcomputerx.formusic.event.EventId;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.ui.adapter.PlayListAdapter;
import com.ximcomputerx.formusic.utils.SharedPreferencesUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class PlayListDialog extends BaseDialog {
    @Bind(R.id.rv_playlist)
    protected RecyclerView rv_playlist;
    @Bind(R.id.ll_playlist)
    protected LinearLayout ll_playlist;
    @Bind(R.id.iv_mode)
    protected ImageView iv_mode;
    @Bind(R.id.tv_mode)
    protected TextView tv_mode;
    @Bind(R.id.tv_number)
    protected TextView tv_number;

    private PlayListAdapter playListAdapter;

    public PlayListDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.dialog_play_list);
    }

    @Override
    protected void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_playlist.setLayoutManager(layoutManager);
        playListAdapter = new PlayListAdapter(PlayManager.getInstance().getMusicList());
        rv_playlist.setAdapter(playListAdapter);
        playListAdapter.setOnItemClickListener(new PlayListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, Object data) {
                PlayManager.getInstance().stopPlayer();
                PlayManager.getInstance().setPlayPosition(position);
                PlayManager.getInstance().playPause();
            }
        });
        rv_playlist.smoothScrollToPosition(PlayManager.getInstance().getPlayPosition());

        initPlayMode();
        tv_number.setText("(共" + PlayManager.getInstance().getMusicList().size() + "首)");
    }

    @OnClick({R.id.ll_mode})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ll_mode:
                switchPlayMode();
                break;
        }
    }

    /**
     * EVENBUS RECIVER
     * @param messageEvent
     */
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessageId()) {
            case EventId.EVENT_ID_MUSIC_TIME:
                playListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void switchPlayMode() {
        PlayModeEnum mode = PlayModeEnum.valueOf(SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, 0));
        switch (mode) {
            case LOOP:
                mode = PlayModeEnum.SHUFFLE;
                //ToastUtil.showShort(getContext(), getContext().getString(R.string.mode_shuffle));
                break;
            case SHUFFLE:
                mode = PlayModeEnum.SINGLE;
                //ToastUtil.showShort(getContext(), getContext().getString(R.string.mode_one));
                break;
            case SINGLE:
                mode = PlayModeEnum.LOOP;
                //ToastUtil.showShort(getContext(), getContext().getString(R.string.mode_loop));
                break;
        }
        SharedPreferencesUtil.setIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, mode.value());
        initPlayMode();
    }

    private void initPlayMode() {
        int mode = SharedPreferencesUtil.getIntPreferences(Constant.PREFERENCES, Constant.PLAY_MODE, 0);
        iv_mode.setImageLevel(mode);
        switch (mode) {
            case 0:
                tv_mode.setText("列表循环");
                break;
            case 1:
                tv_mode.setText("随机播放");
                break;
            case 2:
                tv_mode.setText("单曲循环");
                break;
        }
    }
}
