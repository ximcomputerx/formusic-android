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
import com.ximcomputerx.formusic.model.MixInfo;
import com.ximcomputerx.formusic.util.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.util.TextViewBinder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListMixAdapter extends RecyclerView.Adapter<ListMixAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<MixInfo> dataList;
    private RecyclerView recyclerView;

    public ListMixAdapter(Context context, List<MixInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ListMixAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mix, parent, false);
        view.setOnClickListener(this);
        ListMixAdapter.ViewHolder holder = new ListMixAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListMixAdapter.ViewHolder holder, int position) {
        // 解决图片加载闪动问题
        //if (!dataList.get(position).getCoverImgUrl().equals(holder.iv_image.getTag(R.id.iv_image))) {
            // 加载图片
            GlideImageLoaderUtil.displayRoundImage(dataList.get(position).getCoverImgUrl(), holder.iv_image, R.mipmap.default_cover);
            // 给图片设置标记
            //holder.iv_image.setTag(R.id.iv_image, dataList.get(position).getCoverImgUrl());
        //}
        TextViewBinder.setTextView(holder.tv_update, dataList.get(position).getTrackCount());
        TextViewBinder.setTextView(holder.tv_name, dataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

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

    public int addItems(List<MixInfo> items) {
        if (items == null || items.size() < 1) {
            return 0;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(items);
        //notifyDataSetChanged();
        notifyItemRangeInserted(dataList.size() - items.size(), items.size());
        return items.size();
    }

}
