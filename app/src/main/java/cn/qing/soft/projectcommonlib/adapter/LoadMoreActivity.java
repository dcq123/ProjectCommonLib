package cn.qing.soft.projectcommonlib.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.qing.soft.adapter.LoadNextPageInterface;
import cn.qing.soft.adapter.ViewHolder;
import cn.qing.soft.adapter.recyclerview.LoadMoreRecyclerAdapter;
import cn.qing.soft.adapter.recyclerview.OnItemClickListener;
import cn.qing.soft.projectcommonlib.R;

public class LoadMoreActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    TestRecyclerAdapter mAdapter;
    int totalCount = 100;
    int mCurrentPage = 0;

    boolean isLinearManager = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);


        mAdapter = new TestRecyclerAdapter(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        loadData();

        mAdapter.setLoadNextPageInterface(new LoadNextPageInterface() {
            @Override
            public void loadNextPage(int currentPage) {
                mCurrentPage = currentPage;
                loadData();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, String s, int position) {
                Toast.makeText(LoadMoreActivity.this, "currentPosition:" + s, Toast.LENGTH_SHORT).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                mAdapter.resetAdapter();
                loadData();
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 10) {
                List<String> data = (List<String>) msg.obj;
                mAdapter.setData(data, totalCount);
            }

            swipeRefreshLayout.setRefreshing(false);

        }
    };

    private void loadData() {
        System.out.println("loadData------> mCurrentPage=" + mCurrentPage);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mCurrentPage > 0) {
                    SystemClock.sleep(1000);
                }
                List<String> data = new ArrayList<>();
                int size = mAdapter.getData().size();
                for (int i = size; i < size + 20; i++) {
                    data.add("item" + i);
                }
                Message message = Message.obtain();
                message.what = 10;
                message.obj = data;
                handler.sendMessage(message);
            }
        }).start();

    }

    public void switchLayoutManager() {
        isLinearManager = !isLinearManager;
        if (isLinearManager) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        mAdapter.notifyDataSetChanged();
    }

    class TestRecyclerAdapter extends LoadMoreRecyclerAdapter<String> {

        Random random = new Random();

        public TestRecyclerAdapter(Context context, RecyclerView recyclerView) {
            super(context, recyclerView);
            setHasHeaderView(true);
        }

        @Override
        public int getNormalLayout() {
            return R.layout.item_list;
        }

        @Override
        public void convert(ViewHolder holder, String s) {
            View convertView = holder.getConvertView();
            int height = random.nextInt(200);
            if (height < 80) {
                height = 80;
            }
            convertView.getLayoutParams().height = dp2px(mContext, height);
            convertView.requestLayout();
            holder.setText(R.id.id_item_list_title, s);
        }

        @Override
        public int getHeaderLayout() {
            return R.layout.header_view;
        }

        @Override
        protected void convertHeaderView(ViewHolder holder) {
            holder.setText(R.id.headerTitle, "点击切换布局");
            holder.setOnClickListener(R.id.headerTitle, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchLayoutManager();
                }
            });
        }

        public int dp2px(Context context, float dp) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dp * scale + 0.5f);
        }

    }

}
