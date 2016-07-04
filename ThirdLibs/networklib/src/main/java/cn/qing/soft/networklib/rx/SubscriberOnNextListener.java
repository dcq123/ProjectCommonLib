package cn.qing.soft.networklib.rx;

/**
 * 用于响应Rx的onNext回调接口
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
