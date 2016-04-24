package cn.qing.soft.projectcommonlib.network.service;

import cn.qing.soft.networklib.NetworkCallback;
import cn.qing.soft.networklib.NetworkResponse;
import cn.qing.soft.networklib.RetrofitServiceGenerator;
import cn.qing.soft.projectcommonlib.network.model.PicModel;
import retrofit2.Call;


public class PicServiceFactory {

    private static PicServiceFactory instance;

    private PicService service;

    private PicServiceFactory() {
        service = RetrofitServiceGenerator.createService(PicService.class);
    }

    public static PicServiceFactory getInstance() {
        if (instance == null) {
            synchronized (PicServiceFactory.class) {
                if (instance == null) {
                    instance = new PicServiceFactory();
                }
            }
        }
        return instance;
    }

    public Call doGetPicUrlList(String userName, String sign, NetworkResponse<PicModel> networkResponse) {
        Call<PicModel> call = service.getPicUrlList(userName, sign);
        call.enqueue(new NetworkCallback<PicModel>(networkResponse));
        return call;
    }

}
