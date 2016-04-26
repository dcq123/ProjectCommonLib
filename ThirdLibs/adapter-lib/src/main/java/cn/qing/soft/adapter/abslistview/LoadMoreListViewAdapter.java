package cn.qing.soft.adapter.abslistview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import cn.qing.soft.adapter.LoadNextPageInterface;
import cn.qing.soft.adapter.R;
import cn.qing.soft.adapter.ViewHolder;

/**
 * 支持ListView加载更多的Adapter
 *
 * @param <T>
 */
public abstract class LoadMoreListViewAdapter<T> extends CommonListViewAdapter<T> {

    private static final String TAG = "LoadMoreListViewAdapter";

    // 布局类型编码，对于ListView的类型，必须从0开始往后递增设置，否则会出异常
    private static int TYPE_NORMAL = 0;
    private static int TYPE_LOAD_MORE = 1;
    private static int TYPE_NO_MORE = 2;

    private ListView mListView;

    // 默认加载更多、没有更多的布局提示
    private int loadMoreLayout = R.layout.load_more_layout;
    private int noMoreLayout = R.layout.no_more_layout;

    // 分页使用
    private int mTotalCount = 0;
    private int currentPage = 0;

    // HeaderView、加载更多相关设置
    private boolean isFirst = true;
    private boolean isShowFooter = false;
    private boolean hasNextPage = false;

    /**
     * 定义ListView的多类型支持，使其支持包含HeaderView、正常的item、加载更多、没有更多等布局类型
     */
    private MultiItemTypeSupport<T> typeSupport = new MultiItemTypeSupport<T>() {

        @Override
        public int getLayoutId(int position, T t) {
            int itemType = getItemViewType(position, t);
            if (itemType == TYPE_NORMAL) {
                return getNormalLayout();
            } else if (itemType == TYPE_LOAD_MORE) {
                return loadMoreLayout;
            } else if (itemType == TYPE_NO_MORE) {
                return noMoreLayout;
            }
            return getNormalLayout();
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position, T t) {
            if (isShowFooter && position == getCount() - 1) {
                if (mDataList.size() == mTotalCount) {
                    return TYPE_NO_MORE;
                }
                return TYPE_LOAD_MORE;
            }
            return TYPE_NORMAL;
        }
    };

    public LoadMoreListViewAdapter(Context context, ListView listView) {
        super(context, -1, null);
        mListView = listView;
        mListView.setAdapter(this);
        initScrollListener();
    }

    private void initScrollListener() {
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int totalItem = 0;
            int lastItem = 0;
            boolean isLoadingMore = false;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.i(TAG, "totalItem:" + totalItem + "\t lastItem:" + lastItem + "\t currentPage:" + currentPage);
                if (scrollState == SCROLL_STATE_IDLE && lastItem == totalItem && !isLoadingMore) {
                    currentPage++;
                    loadNextPage(currentPage);
                    isLoadingMore = false;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount;
                totalItem = totalItemCount;
            }
        });
    }

    /**
     * 需要实现的抽象方法，用来设置itemView的布局
     *
     * @return
     */
    public abstract int getNormalLayout();


    /**
     * 设置RecyclerView的数据集，在初始加载到数据，已经在分页加载更多数据时，都需要调用改方法设置数据集
     *
     * @param data
     * @param totalCount
     */
    public void setData(List<T> data, int totalCount) {
        mDataList.addAll(data);
        mTotalCount = totalCount;
        hasNextPage = mDataList.size() < totalCount;
        notifyDataSetChanged();
        if (isFirst) {
            isShowFooter = hasNextPage;
            isFirst = false;
        }
    }

    @Override
    public T getItem(int position) {
        T data = null;
        if (position < mDataList.size()) {
            data = mDataList.get(position);
        }
        return data;
    }

    @Override
    public int getViewTypeCount() {
        return typeSupport.getViewTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = typeSupport.getLayoutId(position, null);
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, layoutId, position);
        if (layoutId == getNormalLayout()) {
            convert(viewHolder, getItem(position));
        }
        return viewHolder.getConvertView();
    }

    @Override
    public int getItemViewType(int position) {
        return typeSupport.getItemViewType(position, null);
    }

    @Override
    public int getCount() {
        int itemCount = mDataList.size();
        if (isShowFooter) {
            return itemCount + 1;
        } else {
            return itemCount;
        }
    }

    public void resetAdapter() {
        isFirst = true;
        isShowFooter = false;
        currentPage = 0;
        mDataList.clear();
    }

    public List<T> getData() {
        return mDataList;
    }

    private LoadNextPageInterface loadNextPageInterface;

    public void setLoadNextPageInterface(LoadNextPageInterface loadNextPageInterface) {
        this.loadNextPageInterface = loadNextPageInterface;
    }


    private void loadNextPage(int nextPage) {
        if (loadNextPageInterface != null && hasNextPage) {
            loadNextPageInterface.loadNextPage(nextPage);
        }
    }
}
