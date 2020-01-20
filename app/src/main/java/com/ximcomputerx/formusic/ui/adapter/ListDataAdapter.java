package com.ximcomputerx.formusic.ui.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @AUTHOR HACKER
 */
public abstract class ListDataAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> dataList = new ArrayList<>();

    public ListDataAdapter(Context context) {
        this.context = context;
    }

    public ListDataAdapter(Context context, List<T> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }

    @Override
    public T getItem(int arg0) {
        return dataList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public int insertItems(Collection<T> items) {
        if (items == null || items.size() < 1) {
            return 0;
        }
        if (dataList == null) {
            dataList = new ArrayList<>(items);
        }
        dataList.addAll(0, items);
        notifyDataSetChanged();
        return items.size();
    }

    public int addItems(Collection<T> items) {
        if (items == null || items.size() < 1) {
            return 0;
        }
        if (dataList == null) {
            dataList = new ArrayList<>(items);
        }
        dataList.addAll(items);
        notifyDataSetChanged();
        return items.size();
    }

    public int addItems(T items) {
        if (items == null) {
            return 0;
        }
        if (dataList == null) {
            dataList = new ArrayList<T>();
        }
        dataList.add(items);
        return dataList.size();
    }

    public int setItems(Collection<T> items) {
        if (dataList != null) {
            dataList.clear();
        }

        if (items == null || items.size() < 1) {
            notifyDataSetChanged();
            return 0;
        }
        dataList.addAll(items);
        notifyDataSetChanged();
        return items.size();
    }
}
