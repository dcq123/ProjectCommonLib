package cn.qing.soft.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.qing.soft.adapter.LoadNextPageInterface;
import cn.qing.soft.adapter.R;
import cn.qing.soft.adapter.ViewHolder;

/**
 * 支持RecyclerView加载更多的Adapter
 *
 * @param <T>
 */
public abstract class LoadMoreRecyclerAdapter<T> extends CommonRecyclerAdapter<T> {

    private static final String TAG = "LoadMoreRecyclerAdapter";

    // 布局类型编码
    private static int TYPE_NORMAL = 100;
    private static int TYPE_LOAD_MORE = 111;
    private static int TYPE_NO_MORE = 222;
    private static int TYPE_HEADER_VIEW = 333;

    private RecyclerView mRecyclerView;

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
    // true代表使用headerView
    private boolean hasHeaderView = false;

    /**
     * 定义RecyclerView的多类型支持，使其支持包含HeaderView、正常的item、加载更多、没有更多等布局类型
     */
    private MultiItemTypeSupport<T> typeSupport = new MultiItemTypeSupport<T>() {
        @Override
        public int getLayoutId(int itemType) {
            if (itemType == TYPE_NORMAL) {
                return getNormalLayout();
            } else if (itemType == TYPE_LOAD_MORE) {
                return loadMoreLayout;
            } else if (itemType == TYPE_NO_MORE) {
                return noMoreLayout;
            } else if (itemType == TYPE_HEADER_VIEW) {
                return getHeaderLayout();
            }
            return getNormalLayout();
        }

        @Override
        public int getItemViewType(int position, T t) {
            if (hasHeaderView && position == 0) {
                return TYPE_HEADER_VIEW;
            }
            if (isShowFooter && position == getItemCount() - 1) {
                if (mDataList.size() == mTotalCount) {
                    return TYPE_NO_MORE;
                }
                return TYPE_LOAD_MORE;
            }
            return TYPE_NORMAL;
        }
    };

    public LoadMoreRecyclerAdapter(Context context, RecyclerView recyclerView) {
        super(context, -1, null);
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(this);
        initScrollListener();
    }

    private void initScrollListener() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;
            boolean isLoadingMore = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                isSlidingToLast = dy > 0;
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisibleItemPosition;
                    // 获取最后一个正在显示的Item的位置
                    if (layoutManager instanceof GridLayoutManager) {
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                        lastVisibleItemPosition = findMax(into);
                    } else {
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
                    }

                    if (isSlidingToLast && layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1) {
                        if (isLoadingMore) {
                            Log.d(TAG, "RecyclerView is loading...");
                        } else {
                            Log.d(TAG, "RecyclerView prepare load next page ...");
                            currentPage++;
                            loadNextPage(currentPage);
                            isLoadingMore = false;
                        }
                    }
                }
            }

            private int findMax(int[] lastPositions) {
                int max = lastPositions[0];
                for (int value : lastPositions) {
                    if (value > max) {
                        max = value;
                    }
                }
                return max;
            }
        });
    }

    /**
     * 需要实现的抽象方法，用来设置itemView的布局
     *
     * @return
     */
    public abstract int getNormalLayout();


    /*********************************************************
     * Header View相关设置，在需要显示headerView时，需要将hasHeaderView设置为true
     * 同时需要覆盖getHeaderLayout()，也可以覆盖convertHeaderView()方法，
     * 用来设置HeaderView中各个控件的数据
     ***********************************************************/
    public void setHasHeaderView(boolean hasHeaderView) {
        this.hasHeaderView = hasHeaderView;
    }

    public int getHeaderLayout() {
        return -1;
    }

    protected void convertHeaderView(ViewHolder holder) {
    }

    /**************************************************/


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


    /**
     * 设置RecyclerView中Item的点击、长按监听
     *
     * @param parent
     * @param viewHolder
     * @param viewType
     */
    @Override
    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (hasHeaderView) {
                        position -= 1;
                    }
                    mOnItemClickListener.onItemClick(parent, v, mDataList.get(position), position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (hasHeaderView) {
                        position -= 1;
                    }
                    return mOnItemLongClickListener.onItemLongClick(parent, v, mDataList.get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = typeSupport.getLayoutId(viewType);
        ViewHolder holder = ViewHolder.get(mContext, null, parent, layoutId, -1);
        if (viewType == TYPE_NORMAL) {
            setListener(parent, holder, viewType);
        } else {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                ((StaggeredGridLayoutManager.LayoutParams) holder.getConvertView().getLayoutParams()).setFullSpan(true);
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int itemType = typeSupport.getItemViewType(position, null);
        if (position == 0 && itemType == TYPE_HEADER_VIEW) {
            convertHeaderView(holder);
        }
        if (itemType == TYPE_NORMAL) {
            holder.updatePosition(position);
            convert(holder, getPositionData(position));
        }
    }

    private T getPositionData(int position) {
        T data = null;
        int dataPosition = hasHeaderView ? position - 1 : position;
        if (dataPosition >= 0 && dataPosition < mDataList.size()) {
            data = mDataList.get(dataPosition);
        }
        return data;
    }


    @Override
    public int getItemViewType(int position) {
        return typeSupport.getItemViewType(position, getPositionData(position));
    }

    @Override
    public int getItemCount() {
        int itemCount = mDataList.size();
        if (hasHeaderView) {
            itemCount += 1;
        }
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
