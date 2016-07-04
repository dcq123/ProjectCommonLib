package cn.qing.soft.networklib.rx;

import cn.qing.soft.networklib.exception.ApiException;
import rx.functions.Func1;

/**
 * 相同格式的Http请求数据统一进行预处理
 * <p/>
 * {
 * "resultCode": 0,
 * "resultMessage": "成功",
 * "data": {}
 * }
 * <p/>
 * Activity或Fragment对resultCode和resultMessage基本没有兴趣，他们只对请求状态和data数据感兴趣。
 * <p/>
 * 基于这种考虑，我们在resultCode != 0的时候，抛出个自定义的ApiException。这样就会进入到subscriber的onError中，我们可以在onError中处理错误信息。
 */
public class RxHttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Override
    public T call(HttpResult<T> tHttpResult) {
        if (tHttpResult.isError()) {
            throw new ApiException("访问接口失败..");
        }
        return tHttpResult.getResults();
    }
}

