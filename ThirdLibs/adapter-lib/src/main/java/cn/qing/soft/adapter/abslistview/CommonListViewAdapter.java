package cn.qing.soft.adapter.abslistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;

import cn.qing.soft.adapter.ViewHolder;

public abstract class CommonListViewAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDataList = new ArrayList<>();
    protected LayoutInflater mInflater;
    private int layoutId;

    public CommonListViewAdapter(Context context, int layoutId, List<T> dataList) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        if (dataList != null) {
            mDataList.clear();
            mDataList.addAll(dataList);
        }
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);

}
