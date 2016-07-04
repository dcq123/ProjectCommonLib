package cn.qing.soft.projectcommonlib.network.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dcq on 2016/4/24 0024.
 */
public abstract class BaseServiceFactory {

    protected static final String baseUrl = "http://rap.taobao.org/mockjsdata/1671/";

    protected Map<String, String> headerMap = new HashMap<>();

    public BaseServiceFactory() {
        initHeaderMap();
    }

    /**
     * 初始化请求头
     */
    private void initHeaderMap() {
        headerMap.put("userId", "123456");
        headerMap.put("sign", "asdhfjasdfasfdhjk");
    }

    protected Map<String, String> createParamMap() {
        return new HashMap<>();
    }


}
