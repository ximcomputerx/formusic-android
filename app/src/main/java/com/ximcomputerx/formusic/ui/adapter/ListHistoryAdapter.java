package com.ximcomputerx.formusic.ui.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ximcomputerx.formusic.R;
import com.ximcomputerx.formusic.model.HistoryMusicInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.util.TextViewBinder;
import com.ximcomputerx.formusic.view.HistoryDetailDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ViewHolder> implements View.OnClickListener {
    private List<HistoryMusicInfo> dataList;
    private RecyclerView recyclerView;
    private String songId = null;
    private Context context;

    public ListHistoryAdapter(Context context, List<HistoryMusicInfo> dataList) {
        this.dataList = dataList;
        this.context = context;

        if (PlayManager.getInstance().getPlayMusic() != null) {
            songId = PlayManager.getInstance().getPlayMusic().getSongId() + "";
        }
    }

    public ListHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songlist_list, parent, false);
        view.setOnClickListener(this);
        ListHistoryAdapter.ViewHolder holder = new ListHistoryAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHistoryAdapter.ViewHolder holder, final int position) {
        holder.iv_vip.setVisibility(View.INVISIBLE);
        TextViewBinder.setTextView(holder.tv_number, (position + 1) + "");
        TextViewBinder.setTextView(holder.tv_title, dataList.get(position).getTitle());
        TextViewBinder.setTextView(holder.tv_artist, dataList.get(position).getArtist());
        TextViewBinder.setTextView(holder.tv_albem, dataList.get(position).getAlbum());

        if (songId != null) {
            if (songId.equals(dataList.get(position).getSongId() + "")) {
                holder.tv_number.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_artist.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_albem.setTextColor(context.getResources().getColor(R.color.red));
                holder.tv_line.setTextColor(context.getResources().getColor(R.color.red));
                holder.pb_progress_bar.setVisibility(View.VISIBLE);
            } else {
                holder.tv_number.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_title.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_artist.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_albem.setTextColor(context.getResources().getColor(R.color.black));
                holder.tv_line.setTextColor(context.getResources().getColor(R.color.black));
                holder.pb_progress_bar.setVisibility(View.GONE);
            }
        }

        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailDialog(dataList.get(position));
            }
        });

        if (dataList.get(position).getFee() == 1) {
            holder.iv_vip.setVisibility(View.VISIBLE);
        }
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
        protected ImageView iv_more;
        @Bind(R.id.tv_line)
        protected TextView tv_line;
        @Bind(R.id.pb_progress_bar)
        protected ProgressBar pb_progress_bar;
        @Bind(R.id.iv_vip)
        protected ImageView iv_vip;

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

    private ListHistoryAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListHistoryAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object data);
    }

    public List<HistoryMusicInfo> getDataList() {
        return dataList;
    }

    public void setDataList(List<HistoryMusicInfo> dataList) {
        this.dataList = dataList;
    }

    public void setPlaySongId(String songId) {
        if (songId == null) {
            this.songId = dataList.get(0).getSongId() + "";
        } else {
            this.songId = songId;
        }
    }

    public void showDetailDialog(HistoryMusicInfo historyMusicInfo) {
        HistoryDetailDialog historyDetailDialog = new HistoryDetailDialog(context, historyMusicInfo);
        historyDetailDialog.setGravity(Gravity.BOTTOM);
        historyDetailDialog.show();
        historyDetailDialog.setFullScreen(true);
    }
}
