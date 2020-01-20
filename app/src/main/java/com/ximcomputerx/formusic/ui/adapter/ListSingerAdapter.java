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
import com.ximcomputerx.formusic.model.SingerInfo;
import com.ximcomputerx.formusic.utils.GlideImageLoaderUtil;
import com.ximcomputerx.formusic.utils.TextViewBinder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListSingerAdapter extends RecyclerView.Adapter<ListSingerAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<SingerInfo> dataList;
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

    public ListSingerAdapter(Context context, List<SingerInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public ListSingerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_singer_list, parent, false);
        view.setOnClickListener(this);
        ListSingerAdapter.ViewHolder holder = new ListSingerAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListSingerAdapter.ViewHolder holder, int position) {
        GlideImageLoaderUtil.displayRoundImage(dataList.get(position).getPicUrl(), holder.iv_image, R.mipmap.default_cover);
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

    public int addItems(List<SingerInfo> items) {
        if (items == null || items.size() < 1) {
            return 0;
        }
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(items);
        notifyDataSetChanged();
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
