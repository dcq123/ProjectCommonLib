package cn.qing.soft.adapter.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView中的item长按事件监听
 */
public interface OnItemLongClickListener<T> {
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
}
