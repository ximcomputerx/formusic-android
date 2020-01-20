package com.ximcomputerx.formusic.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.ximcomputerx.formusic.R;

/**
 * @AUTHOR HACKER
 */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private LayoutInflater inflater;
    private OnLoadListener loadListener;

    private boolean canLoadMore = true;
    private boolean mIsLoadingMore = false;
    private boolean isBottom = true;

    private View footView;
    private ProgressWheel footProgressBar;
    private TextView footTxt;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setCacheColorHint(Color.TRANSPARENT);
        inflater = LayoutInflater.from(context);
        footView = inflater.inflate(R.layout.item_load_list_foot, null);
        footProgressBar = (ProgressWheel) footView.findViewById(R.id.foot_bar);
        footTxt = (TextView) footView.findViewById(R.id.foot_txt);
        addFooterView(footView);
        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
        isBottom = totalItemCount <= firstVisiableItem + visibleItemCount;
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && isBottom && !mIsLoadingMore) {
            scroll2LoadMore();
        }
    }


    private synchronized void scroll2LoadMore() {
        if (loadListener != null && canLoadMore) {
            canLoadMore = false;
            footProgressBar.setVisibility(View.VISIBLE);
            footTxt.setVisibility(GONE);
            mIsLoadingMore = true;
            loadListener.onLoadMore();
        }
    }

    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
    }

    public void onLoadMoreFailed() {
        footProgressBar.setVisibility(View.GONE);
        footTxt.setVisibility(VISIBLE);
        footTxt.setText(R.string.load_failed);
    }

    public void setLoadable(boolean loadable) {
        canLoadMore = loadable;
        footTxt.setText(loadable ? R.string.loading_data : R.string.load_no_more);
        if (!loadable) {
            footProgressBar.setVisibility(View.GONE);
            footTxt.setVisibility(VISIBLE);
        } else {
            footProgressBar.setVisibility(VISIBLE);
            footTxt.setVisibility(GONE);
        }
    }

    public void setIsVisibleProgress(boolean isVisible) {
        if (isVisible) {
            footProgressBar.setVisibility(View.VISIBLE);
        } else {
            footProgressBar.setVisibility(View.GONE);
        }
    }

    public void setFirstLoadable(boolean loadable) {
        canLoadMore = loadable;
        if (loadable) {
            if (getFooterViewsCount() == 0) {
                addFooterView(footView);
                footProgressBar.setVisibility(VISIBLE);
                footTxt.setVisibility(GONE);
            }
        } else {
            if (getFooterViewsCount() > 0) {
                removeFooterView(footView);
            }
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public interface OnLoadListener {
        void onLoadMore();
    }
}
