package cn.soft.qing.apptemplate.data.remote;


import java.util.Map;

import cn.soft.qing.apptemplate.data.model.PicModel;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface NetworkService {

    String BASE_URL = "http://rap.taobao.org/mockjsdata/1671/";

    @FormUrlEncoded
    @POST("getPicUrlList")
    Observable<PicModel> getPicUrlList(@Field("userName") String userName,
                                       @Field("sign") String sign, @FieldMap Map<String, String> headerMap);

}
