package cn.qing.soft.networklib;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Retrofit请求的响应回调封装类
 */
public class NetworkCallback<MODEL> implements Callback<MODEL> {

    private NetworkResponse<MODEL> networkResponse;

    public NetworkCallback(NetworkResponse<MODEL> networkResponse) {
        this.networkResponse = networkResponse;
    }

    @Override
    public void onResponse(Call<MODEL> call, Response<MODEL> response) {
        boolean isTrue = networkResponse.onFinish();
        if (isTrue) {
            BaseModel baseModel = (BaseModel) response.body();
            MODEL result = response.body();
            if (isSuccess(baseModel)) {
                networkResponse.onSuccess(result);
            } else {
                networkResponse.onFail(result, null);
            }
        }
    }

    @Override
    public void onFailure(Call<MODEL> call, Throwable t) {
        boolean isTrue = networkResponse.onFinish();
        if (isTrue) {
            networkResponse.onFail(null, t);
        }
    }

    public boolean isSuccess(BaseModel baseModel) {
        if (NetworkStatusCode.CODE_SUCCESS.equals(baseModel.getStatus())) {
            return true;
        } else if (NetworkStatusCode.CODE_FAIL.equals(baseModel.getStatus())) {
            return false;
        }
        return false;
    }

}
