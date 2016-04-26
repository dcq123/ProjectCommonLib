package cn.qing.soft.projectcommonlib.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.qing.soft.adapter.LoadNextPageInterface;
import cn.qing.soft.adapter.ViewHolder;
import cn.qing.soft.adapter.abslistview.LoadMoreListViewAdapter;
import cn.qing.soft.projectcommonlib.R;

public class LoadMoreListViewActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    TestListViewAdapter mAdapter;
    int totalCount = 60;
    int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_list_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new TestListViewAdapter(this, listView);

        loadData();

        mAdapter.setLoadNextPageInterface(new LoadNextPageInterface() {
            @Override
            public void loadNextPage(int currentPage) {
                mCurrentPage = currentPage;
                loadData();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = mAdapter.getItem(position);
                if (msg != null) {

                    Toast.makeText(LoadMoreListViewActivity.this, "click item position:" + msg, Toast.LENGTH_SHORT).show();
                }
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
                for (int i = size; i < size + 10; i++) {
                    data.add("item" + i);
                }
                Message message = Message.obtain();
                message.what = 10;
                message.obj = data;
                handler.sendMessage(message);
            }
        }).start();

    }

    class TestListViewAdapter extends LoadMoreListViewAdapter<String> {

        public TestListViewAdapter(Context context, ListView listView) {
            super(context, listView);
        }

        @Override
        public int getNormalLayout() {
            return R.layout.item_list;
        }

        @Override
        public void convert(ViewHolder holder, String s) {

            holder.setText(R.id.id_item_list_title, s);

        }
    }

}
