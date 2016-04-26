package cn.qing.soft.adapter.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView中的item点击事件监听
 *
 * @param <T>
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewGroup parent, View view, T t, int position);
}