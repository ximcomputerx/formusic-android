package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.SongInfo;
import com.ximcomputerx.formusic.util.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.util.TextViewBinder;

import butterknife.Bind;

public class SongDetailDialog extends BaseDialog {
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

    private SongInfo songInfo;

    public SongDetailDialog(Context context, SongInfo songInfo) {
        super(context, R.style.CustomDialog);
        this.songInfo = songInfo;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.dialog_song_detail_list);
    }

    @Override
    protected void initData() {
        GlideImageLoaderUtil.displayRoundImage(songInfo.getAl().getPicUrl(), iv_pic, R.mipmap.default_cover);
        TextViewBinder.setTextView(tv_title, songInfo.getName());
        TextViewBinder.setTextView(tv_singer, songInfo.getAr().get(0).getName());
        TextViewBinder.setTextView(tv_artist, songInfo.getAr().get(0).getName());
        TextViewBinder.setTextView(tv_albem, songInfo.getAl().getName());
        tv_title.setSelected(true);
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
