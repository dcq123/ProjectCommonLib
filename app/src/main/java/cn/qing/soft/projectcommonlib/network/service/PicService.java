package cn.qing.soft.projectcommonlib.network.service;

import java.util.Map;

import cn.qing.soft.projectcommonlib.network.model.PicModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by dcq on 2016/4/20 0020.
 */
public interface PicService {


    @FormUrlEncoded
    @POST("getPicUrlList")
    Call<PicModel> getPicUrlList(@Field("userName") String userName,
                                 @Field("sign") String sign);


    @FormUrlEncoded
    @POST("getPicUrlList")
    Call<PicModel> getPicUrlListByFieldMap(@FieldMap Map<String, String> param);

}
