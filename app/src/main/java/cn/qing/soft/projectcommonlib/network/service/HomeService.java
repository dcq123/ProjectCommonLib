package cn.qing.soft.projectcommonlib.network.service;

import cn.qing.soft.projectcommonlib.network.model.StatisticsInfoModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 获取首页数据service
 */
public interface HomeService {

    @FormUrlEncoded
    @POST("getStatisticsInfo")
    Call<StatisticsInfoModel> getStatisticsInfo(@Field("customerId") String customerId,
                                                @Field("sign") String sign,
                                                @Field("userName") String userName);

}
