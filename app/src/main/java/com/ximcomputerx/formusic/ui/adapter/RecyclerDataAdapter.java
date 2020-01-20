package com.ximcomputerx.formusic.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

abstract class RecyclerDataAdapter<T> extends RecyclerView.Adapter<RecyclerDataAdapter.ViewHolder> {
    private List<T> mDatas;

    /**
     * 多参的构造方法
     * @param datas
     */
    public RecyclerDataAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public abstract int getLayoutId(int viewType);

    /**
     * 创建视图
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(parent, getLayoutId(viewType));
    }

    /**
     * 填充数据
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public abstract void convert(ViewHolder holder, T data, int position);

    /**
     * 初始化控件
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mViews;
        private View mConvertView;

        private ViewHolder(View v) {
            super(v);
            mConvertView = v;
            mViews = new SparseArray<>();
        }

        public static ViewHolder get(ViewGroup parent, int layoutId) {
            View convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new ViewHolder(convertView);
        }

        public <T extends View> T getView(int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mConvertView.findViewById(id);
                mViews.put(id, v);
            }
            return (T) v;
        }

        public void setText(int id, String value) {
            TextView view = getView(id);
            view.setText(value);
        }
    }
}
