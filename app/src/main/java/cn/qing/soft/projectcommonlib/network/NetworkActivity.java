package cn.qing.soft.projectcommonlib.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.qing.soft.networklib.NetworkResponse;
import cn.qing.soft.projectcommonlib.R;
import cn.qing.soft.projectcommonlib.network.model.PicModel;
import cn.qing.soft.projectcommonlib.network.model.StatisticsInfoModel;
import cn.qing.soft.projectcommonlib.network.service.HomeServiceFactory;
import cn.qing.soft.projectcommonlib.network.service.PicServiceFactory;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
    }

    public void testNetwork(View view) {
        HomeServiceFactory.getInstance().getStatisticsInfo("ding", "chang", "qing", new NetworkResponse<StatisticsInfoModel>() {
            @Override
            public void onSuccess(StatisticsInfoModel statisticsInfoModel) {
                System.out.println("请求成功：" + statisticsInfoModel.toString());
            }

            @Override
            public void onFail(StatisticsInfoModel statisticsInfoModel, Throwable throwable) {
                System.out.println("请求失败...");
            }

            @Override
            public boolean onFinish() {
                return true;
            }
        });
    }


    public void testNetwork2(View view) {
        PicServiceFactory.getInstance().doGetPicUrlList("qing", "ding", new NetworkResponse<PicModel>() {
            @Override
            public void onFail(PicModel picModel, Throwable throwable) {
                System.out.println("请求失败...");
            }

            @Override
            public boolean onFinish() {
                System.out.println("请求结束");
                return true;
            }

            @Override
            public void onSuccess(PicModel picModel) {

                System.out.println("请求成功：" + picModel.toString());
            }
        });

    }
}
