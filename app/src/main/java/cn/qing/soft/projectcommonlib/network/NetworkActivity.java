package cn.qing.soft.projectcommonlib.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.qing.soft.networklib.RetrofitServiceFactory;
import cn.qing.soft.projectcommonlib.R;
import cn.qing.soft.projectcommonlib.network.model.PicModel;
import cn.qing.soft.projectcommonlib.network.service.PicService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
    }

    public void testNetwork(View view) {

        PicService service = RetrofitServiceFactory.createService(PicService.class);
        service.getPicUrlList("ding", "qing")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PicModel>() {
                    @Override
                    public void onCompleted() {

                        System.out.println("请求结束...");
                    }

                    @Override
                    public void onError(Throwable e) {

                        System.out.println("请求出现异常...");

                    }

                    @Override
                    public void onNext(PicModel picModel) {

                        System.out.println("请求成功：" + picModel);

                    }
                });
    }
}
