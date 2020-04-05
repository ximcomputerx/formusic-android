package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.event.MessageEvent;
import com.ximcomputerx.formusic.model.LikeMusicInfo;
import com.ximcomputerx.formusic.util.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.util.TextViewBinder;

import butterknife.Bind;

public class LikeDetailDialog extends BaseDialog {
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

    private LikeMusicInfo likeMusicInfo;

    public LikeDetailDialog(Context context, LikeMusicInfo likeMusicInfo) {
        super(context, R.style.CustomDialog);
        this.likeMusicInfo = likeMusicInfo;
    }

    @Override
    protected void initView() {
        setContentView(R.layout.dialog_song_detail_list);
    }

    @Override
    protected void initData() {
        GlideImageLoaderUtil.displayRoundImage(likeMusicInfo.getCoverPath(), iv_pic, R.mipmap.default_cover);
        TextViewBinder.setTextView(tv_title, likeMusicInfo.getTitle());
        TextViewBinder.setTextView(tv_singer, likeMusicInfo.getArtist());
        TextViewBinder.setTextView(tv_artist, likeMusicInfo.getArtist());
        TextViewBinder.setTextView(tv_albem, likeMusicInfo.getAlbum());
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
