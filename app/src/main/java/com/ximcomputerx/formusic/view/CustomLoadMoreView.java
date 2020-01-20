package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.utils.ScreenUtils;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

public class CustomLoadMoreView extends LinearLayout implements SwipeRecyclerView.LoadMoreView, View.OnClickListener {
    private SwipeRecyclerView.LoadMoreListener mLoadMoreListener;
    private View vFooter;

    public CustomLoadMoreView(Context context) {
        super(context);
        setOnClickListener(this);
        vFooter = LayoutInflater.from(getContext()).inflate(R.layout.auto_load_list_view_footer, null);
        this.addView(vFooter);
        ScreenUtils.init(context);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vFooter.getLayoutParams();
        params.width = ScreenUtils.dp2px(ScreenUtils.getScreenWidth());
        vFooter.setLayoutParams(params);

        this.setGravity(Gravity.CENTER);
    }


    /**
     * 马上开始回调加载更多了，这里应该显示进度条。
     */
    @Override
    public void onLoading() {
        // 展示加载更多的动画和提示信息。
        setVisibility(VISIBLE);
    }

    /**
     * 加载更多完成了。
     * @param dataEmpty 是否请求到空数据。
     * @param hasMore   是否还有更多数据等待请求。
     */
    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore) {
        // 根据参数，显示没有数据的提示、没有更多数据的提示。
        // 如果都不存在，则都不用显示。
        if (!hasMore) {
            //setVisibility(VISIBLE);
            setVisibility(INVISIBLE);
            if (dataEmpty) {
                //mTvMessage.setText("暂时没有数据");
            } else {
                //mTvMessage.setText("没有更多数据啦");
            }
        } else {
            setVisibility(INVISIBLE);
        }
    }

    /**
     * 加载出错啦，下面的错误码和错误信息二选一。
     * @param errorCode    错误码。
     * @param errorMessage 错误信息。
     */
    @Override
    public void onLoadError(int errorCode, String errorMessage) {

    }

    /**
     * 调用了setAutoLoadMore(false)后，在需要加载更多的时候，此方法被调用，并传入listener。
     */
    @Override
    public void onWaitToLoadMore(SwipeRecyclerView.LoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    /**
     * 非自动加载更多时mLoadMoreListener才不为空。
     */
    @Override
    public void onClick(View v) {
        if (mLoadMoreListener != null) mLoadMoreListener.onLoadMore();
    }
}
