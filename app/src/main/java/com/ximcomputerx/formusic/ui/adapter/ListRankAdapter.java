package com.ximcomputerx.formusic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.RankInfo;
import com.ximcomputerx.formusic.util.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.util.TextViewBinder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListRankAdapter extends RecyclerView.Adapter<ListRankAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<RankInfo> dataList;
    private RecyclerView recyclerView;

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ll_child)
        protected LinearLayout ll_child;
        @Bind(R.id.iv_image)
        protected ImageView iv_image;
        @Bind(R.id.tv_update)
        protected TextView tv_update;
        @Bind(R.id.tv_name)
        protected TextView tv_name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ListRankAdapter(Context context, List<RankInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public ListRankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rank_list, parent, false);
        view.setOnClickListener(this);
        ListRankAdapter.ViewHolder holder = new ListRankAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListRankAdapter.ViewHolder holder, int position) {
        GlideImageLoaderUtil.displayRoundImage(dataList.get(position).getCoverImgUrl(), holder.iv_image, R.mipmap.default_cover);
        TextViewBinder.setTextView(holder.tv_update, dataList.get(position).getUpdateFrequency());
        TextViewBinder.setTextView(holder.tv_name, dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, position, dataList.get(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private ListSongAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListSongAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object data);
    }


}
