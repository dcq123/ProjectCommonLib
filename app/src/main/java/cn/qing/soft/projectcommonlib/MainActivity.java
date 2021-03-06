package cn.qing.soft.projectcommonlib;

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
import cn.qing.soft.projectcommonlib.adapter.MainAdapterActivity;
import cn.qing.soft.projectcommonlib.fgnavigator.FragmentNavigatorActivity;
import cn.qing.soft.projectcommonlib.model.ClickModel;
import cn.qing.soft.projectcommonlib.network.NetworkActivity;

public class MainActivity extends AppCompatActivity {

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

                startActivity(new Intent(MainActivity.this, data.getActivityClass()));
            }
        });
    }

    private void initData() {

        ClickModel fragmentNavi = new ClickModel(FragmentNavigatorActivity.class, "Fragment 导航", "FragmentNavigatorActivity");
        datas.add(fragmentNavi);

        ClickModel network = new ClickModel(NetworkActivity.class, "Retrofit网络框架", "NetworkActivity");
        datas.add(network);

        ClickModel adapterActivity = new ClickModel(MainAdapterActivity.class, "Base Adapter测试", "MainAdapterActivity");
        datas.add(adapterActivity);


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
