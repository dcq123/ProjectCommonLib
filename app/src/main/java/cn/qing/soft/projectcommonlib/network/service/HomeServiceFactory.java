package cn.qing.soft.projectcommonlib.network.service;

import cn.qing.soft.networklib.NetworkCallback;
import cn.qing.soft.networklib.NetworkResponse;
import cn.qing.soft.networklib.RetrofitServiceGenerator;
import cn.qing.soft.projectcommonlib.network.model.StatisticsInfoModel;
import retrofit2.Call;

/**
 * Created by dcq on 2016/4/24 0024.
 */
public class HomeServiceFactory extends BaseServiceFactory {

    private HomeService service;
    private static HomeServiceFactory instance;

    private HomeServiceFactory() {
        super();
        service = RetrofitServiceGenerator.createServiceByHeader(baseUrl, HomeService.class, headerMap);
    }

    public static HomeServiceFactory getInstance() {
        if (instance == null) {
            synchronized (HomeServiceFactory.class) {
                if (instance == null) {
                    instance = new HomeServiceFactory();
                }
            }
        }
        return instance;
    }

    public Call getStatisticsInfo(String customerId, String userName, String sign,
                                  NetworkResponse<StatisticsInfoModel> networkResponse) {
        Call<StatisticsInfoModel> call = service.getStatisticsInfo(customerId, sign, userName);
        call.enqueue(new NetworkCallback<StatisticsInfoModel>(networkResponse));
        return call;
    }

}
