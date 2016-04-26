package cn.qing.soft.projectcommonlib.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qing.soft.adapter.ViewHolder;
import cn.qing.soft.adapter.recyclerview.CommonRecyclerAdapter;
import cn.qing.soft.adapter.recyclerview.OnItemClickListener;
import cn.qing.soft.projectcommonlib.R;
import cn.qing.soft.projectcommonlib.model.ClickModel;

public class MainAdapterActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<ClickModel> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initData();

        MainRecyclerAdapter adapter = new MainRecyclerAdapter(this, R.layout.item_main, datas);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new OnItemClickListener<ClickModel>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, ClickModel data, int position) {

                startActivity(new Intent(MainAdapterActivity.this, data.getActivityClass()));
            }
        });
    }

    private void initData() {

        ClickModel recyclerActivity = new ClickModel(LoadMoreActivity.class, "RecyclerView Adapter", "LoadMoreActivity");
        datas.add(recyclerActivity);

        ClickModel listViewActivity = new ClickModel(LoadMoreListViewActivity.class, "ListView Adapter", "LoadMoreListViewActivity");
        datas.add(listViewActivity);


    }

    class MainRecyclerAdapter extends CommonRecyclerAdapter<ClickModel> {

        public MainRecyclerAdapter(Context context, int layoutId, List<ClickModel> dataList) {
            super(context, layoutId, dataList);
        }

        @Override
        public void convert(ViewHolder holder, ClickModel data) {

            holder.setText(R.id.title, data.getTitle());
            holder.setText(R.id.className, data.getClassName());
        }
    }
}
