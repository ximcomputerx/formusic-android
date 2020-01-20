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
import com.ximcomputerx.formusic.model.SongInfo;
import com.ximcomputerx.formusic.play.PlayManager;
import com.ximcomputerx.formusic.utils.TextViewBinder;
import com.ximcomputerx.formusic.view.SongDetailDialog;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @AUTHOR HACKER
 */
public class ListSongAdapter extends RecyclerView.Adapter<ListSongAdapter.ViewHolder> implements View.OnClickListener {
    private List<SongInfo> dataList;
    private RecyclerView recyclerView;
    private Context context;
    private String songId = null;

    public ListSongAdapter(Context context, List<SongInfo> dataList) {
        this.dataList = dataList;
        this.context = context;

        if (PlayManager.getInstance().getPlayMusic() != null) {
            songId = PlayManager.getInstance().getPlayMusic().getSongId() + "";
        }
    }

    public ListSongAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songlist_list, parent, false);
        view.setOnClickListener(this);
        ListSongAdapter.ViewHolder holder = new ListSongAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListSongAdapter.ViewHolder holder, final int position) {
        TextViewBinder.setTextView(holder.tv_number, (position + 1) + "");
        TextViewBinder.setTextView(holder.tv_title, dataList.get(position).getName());
        TextViewBinder.setTextView(holder.tv_artist, dataList.get(position).getAr().get(0).getName());
        TextViewBinder.setTextView(holder.tv_albem, dataList.get(position).getAl().getName());
        holder.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailDialog(dataList.get(position));
            }
        });

        if (songId != null) {
            if (songId.equals(dataList.get(position).getId())) {
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
    }

    public void showDetailDialog(SongInfo songInfo) {
        SongDetailDialog songDetailDialog = new SongDetailDialog(context, songInfo);
        songDetailDialog.setGravity(Gravity.BOTTOM);
        songDetailDialog.show();
        songDetailDialog.setFullScreen(true);
    }

    public void setPlaySongId(String songId) {
        if (songId == null) {
            this.songId = dataList.get(0).getId();
        } else {
            this.songId = songId;
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
        @Bind(R.id.tv_line)
        protected TextView tv_line;
        @Bind(R.id.tv_albem)
        protected TextView tv_albem;
        @Bind(R.id.iv_more)
        protected ImageView iv_more;
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

    private ListSongAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(ListSongAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, Object data);
    }

}
