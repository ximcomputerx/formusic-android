package com.ximcomputerx.formusic.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.MusicInfo;
import com.ximcomputerx.formusic.utils.TextViewBinder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> implements View.OnClickListener {
    private List<MusicInfo> dataList;
    private RecyclerView recyclerView;

    public PlayListAdapter(List<MusicInfo> dataList) {
        this.dataList = dataList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_list, parent, false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (dataList.get(position).isPlay()) {
            holder.tv_title.setEnabled(true);
            holder.tv_name.setEnabled(true);
            holder.tv_line.setEnabled(true);
            holder.pb_progress_bar.setVisibility(View.VISIBLE);
        } else {
            holder.tv_title.setEnabled(false);
            holder.tv_name.setEnabled(false);
            holder.tv_line.setEnabled(false);
            holder.pb_progress_bar.setVisibility(View.GONE);
        }
        TextViewBinder.setTextView(holder.tv_title, dataList.get(position).getTitle());
        TextViewBinder.setTextView(holder.tv_name, dataList.get(position).getArtist());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_title)
        protected TextView tv_title;
        @Bind(R.id.tv_name)
        protected TextView tv_name;
        @Bind(R.id.tv_line)
        protected TextView tv_line;
        @Bind(R.id.iv_delete)
        protected ImageView iv_delete;
        @Bind(R.id.pb_progress_bar)
        protected ProgressBar pb_progress_bar;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public void onClick(View view) {
        int position = recyclerView.getChildAdapterPosition(view);
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(recyclerView, view, position, dataList.get(position));
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

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        //参数（父组件，当前单击的View,单击的View的位置，数据）
        void onItemClick(RecyclerView parent, View view, int position, Object data);
    }
}
