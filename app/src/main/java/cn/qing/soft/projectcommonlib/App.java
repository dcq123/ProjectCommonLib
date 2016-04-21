package cn.qing.soft.projectcommonlib;

import android.app.Application;

import cn.qing.soft.networklib.RetrofitServiceFactory;

/**
 * Created by dcq on 2016/4/20 0020.
 */
public class App extends Application {

    private static final String baseUrl = "http://rap.taobao.org/mockjsdata/1671/";

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitServiceFactory.init(baseUrl, true);
    }
}
