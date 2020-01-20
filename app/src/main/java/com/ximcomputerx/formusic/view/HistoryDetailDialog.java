package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.HistoryMusicInfo;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.utils.TextViewBinder;

import butterknife.Bind;

public class HistoryDetailDialog extends BaseDialog {
    @Bind(R.id.iv_pic)
    protected ImageView iv_pic;
    @Bind(R.id.tv_title)
    protected TextView tv_title;
    @Bind(R.id.tv_singer)
    protected TextView tv_singer;
    @Bind(R.id.tv_artist)
    protected TextView tv_artist;
    @Bind(R.id.tv_albem)
    protected TextView tv_albem;

    private HistoryMusicInfo historyMusicInfo;

    public HistoryDetailDialog(Context context, HistoryMusicInfo historyMusicInfo) {
        super(context, R.style.CustomDialog);
        this.historyMusicInfo = historyMusicInfo;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.dialog_song_detail_list);
    }

    @Override
    protected void initData() {
        GlideImageLoaderUtil.displayRoundImage(historyMusicInfo.getCoverPath(), iv_pic, R.mipmap.default_cover);
        TextViewBinder.setTextView(tv_title, historyMusicInfo.getTitle());
        TextViewBinder.setTextView(tv_singer, historyMusicInfo.getArtist());
        TextViewBinder.setTextView(tv_artist, historyMusicInfo.getTitle());
        TextViewBinder.setTextView(tv_albem, historyMusicInfo.getAlbum());
    }

    /**
     * EVENBUS RECIVER
     * @param messageEvent
     */
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessageId()) {

        }
    }

}
