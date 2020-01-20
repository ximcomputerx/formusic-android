package com.ximcomputerx.formusic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.SearchHotInfo;
import com.ximcomputerx.formusic.utils.TextViewBinder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListSearchHotAdapter extends RecyclerView.Adapter<ListSearchHotAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private List<SearchHotInfo> dataList;
    private RecyclerView recyclerView;

    public ListSearchHotAdapter(Context context, List<SearchHotInfo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public ListSearchHotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hot, parent, false);
        view.setOnClickListener(this);
        ListSearchHotAdapter.ViewHolder holder = new ListSearchHotAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListSearchHotAdapter.ViewHolder holder, int position) {
        if (position == 0 || position == 1 || position == 2) {
            holder.tv_number.setTextColor(context.getResources().getColor(R.color.red));
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.red));
            holder.tv_artist.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            holder.tv_number.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_title.setTextColor(context.getResources().getColor(R.color.black));
            holder.tv_artist.setTextColor(context.getResources().getColor(R.color.black));
        }
        TextViewBinder.setTextView(holder.tv_number, (position + 1) + "");
        TextViewBinder.setTextView(holder.tv_title, dataList.get(position).getSearchWord());
        TextViewBinder.setTextView(holder.tv_artist, dataList.get(position).getContent());
        TextViewBinder.setTextView(holder.tv_more, dataList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_number)
        protected TextView tv_number;
        @Bind(R.id.tv_title)
        protected TextView tv_title;
        @Bind(R.id.tv_artist)
        protected TextView tv_artist;
        @Bind(R.id.tv_more)
        protected TextView tv_more;

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

    /**
     * 将RecycleView从Adapter解除
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    private ListSearchHotAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListSearchHotAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(View view, int position, Object data);
    }

}
