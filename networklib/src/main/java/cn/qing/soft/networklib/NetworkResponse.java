package cn.qing.soft.networklib;

/**
 * 请求响应回调类
 *
 * @param <MODEL>
 */
public class NetworkResponse<MODEL> {

    public void onSuccess(MODEL model) {

    }

    public void onFail(MODEL model, Throwable throwable) {

    }

    /**
     * onFinish时需要检查当前请求所在的上下文是否还存在，即用来简单判断Activity是否还存在，
     * 如果已经销毁了，就没有必要在回调onSuccess和onFail方法，避免造成异常情况
     *
     * @return
     */
    public boolean onFinish() {
        return false;
    }

}
