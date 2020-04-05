package com.ximcomputerx.formusic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.RecommendIinfo;
import com.ximcomputerx.formusic.util.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.util.TextViewBinder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListRecommendAdapter extends RecyclerView.Adapter<ListRecommendAdapter.ViewHolder> implements View.OnClickListener {
    private List<RecommendIinfo> dataList;
    private RecyclerView recyclerView;

    public ListRecommendAdapter(Context context, List<RecommendIinfo> dataList) {
        this.dataList = dataList;
    }

    public ListRecommendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_list, parent, false);
        view.setOnClickListener(this);
        ListRecommendAdapter.ViewHolder holder = new ListRecommendAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListRecommendAdapter.ViewHolder holder, int position) {
        TextViewBinder.setTextView(holder.tv_number, dataList.get(position).getPlayCount() + "次播放");
        TextViewBinder.setTextView(holder.tv_title, dataList.get(position).getName());
        GlideImageLoaderUtil.displayRoundImage(dataList.get(position).getPicUrl(), holder.iv_pic, R.mipmap.default_cover);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        protected TextView tv_title;
        @Bind(R.id.tv_number)
        protected TextView tv_number;
        @Bind(R.id.iv_pic)
        protected ImageView iv_pic;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

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

    private ListRecommendAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListRecommendAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object data);
    }

}
