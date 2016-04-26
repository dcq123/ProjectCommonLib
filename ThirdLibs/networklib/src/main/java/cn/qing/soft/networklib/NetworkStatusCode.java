package cn.qing.soft.networklib;

/**
 * 网络请求状态码，该状态码与服务器端协商定义，客户端需要解析响应中的状态码，来区分请求是何种状态
 */
public class NetworkStatusCode {

    /**
     * 请求成功
     */
    public static final String CODE_SUCCESS = "Success";

    /**
     * 接口调用成功，服务端内部业务处理失败
     */
    public static final String CODE_FAIL = "Fail";

    /**
     * 警告提示
     */
    public static final String CODE_WARNING = "Warning";

}
