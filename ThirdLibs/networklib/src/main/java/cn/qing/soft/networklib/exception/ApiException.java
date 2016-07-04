package cn.qing.soft.networklib.exception;

/**
 * Created by dcq on 2016/5/4 0004.
 */
public class ApiException extends RuntimeException {

    public ApiException(int resultCode) {
        this(parseResultCode(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    private static String parseResultCode(int resultCode) {
        String detailMessage = "";
        switch (resultCode) {
            case 300:
                detailMessage = "用户不存在";
                break;
            case 400:
                detailMessage = "签名以失效";
                break;
        }

        return detailMessage;
    }
}
