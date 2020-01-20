package com.ximcomputerx.formusic.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.SearchInfo;
import com.ximcomputerx.formusic.utils.TextViewBinder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListSearchDetailAdapter extends RecyclerView.Adapter<ListSearchDetailAdapter.ViewHolder> implements View.OnClickListener {
    private List<SearchInfo> dataList;
    private RecyclerView recyclerView;
    private String songId = null;

    public ListSearchDetailAdapter(Context context, List<SearchInfo> dataList) {
        this.dataList = dataList;
    }

    public ListSearchDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_detail, parent, false);
        view.setOnClickListener(this);
        ListSearchDetailAdapter.ViewHolder holder = new ListSearchDetailAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListSearchDetailAdapter.ViewHolder holder, int position) {
        TextViewBinder.setTextView(holder.tv_number, (position + 1) + "");
        TextViewBinder.setTextView(holder.tv_title, dataList.get(position).getName());
        TextViewBinder.setTextView(holder.tv_artist, dataList.get(position).getArtists().get(0).getName());
        TextViewBinder.setTextView(holder.tv_albem, dataList.get(position).getAlbum().getName());
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
        @Bind(R.id.tv_albem)
        protected TextView tv_albem;
        @Bind(R.id.iv_more)
        protected ImageView v_more;

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

    private ListSearchDetailAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListSearchDetailAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object data);
    }

    public int addItems(List<SearchInfo> items) {
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

    public void setPlaySongId(String songId) {
        if (songId == null) {
            this.songId = dataList.get(0).getId();
        } else {
            this.songId = songId;
        }
    }

}
