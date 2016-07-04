package cn.soft.qing.apptemplate.data;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.soft.qing.apptemplate.data.local.DataBaseHelper;
import cn.soft.qing.apptemplate.data.local.PreferencesHelper;
import cn.soft.qing.apptemplate.data.model.PicModel;
import cn.soft.qing.apptemplate.data.remote.NetworkService;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 数据访问层,所有的本地数据,服务端数据,统一使用DataManager进行访问,该类由Dagger维护管理
 */
@Singleton
public class DataManager {

    private NetworkService networkService;
    private PreferencesHelper preferencesHelper;
    private DataBaseHelper dataBaseHelper;

    @Inject
    public DataManager(NetworkService networkService, PreferencesHelper preferencesHelper, DataBaseHelper dataBaseHelper) {
        this.networkService = networkService;
        this.preferencesHelper = preferencesHelper;
        this.dataBaseHelper = dataBaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    private <T> Observable<T> wrapperRequest(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<PicModel> getPicUrlList(String userName, String sign) {
        return wrapperRequest(networkService.getPicUrlList(userName, sign, null));
    }


}
