package cn.soft.qing.apptemplate.data.remote;

import com.orhanobut.logger.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dingchangqing on 16/7/4.
 * <p/>
 * 网络请求代理类,用来在请求之前添加一些签名字段
 */
public class NetworkHandler implements InvocationHandler {

    private NetworkService networkService;

    public Object bind(NetworkService networkService) {
        this.networkService = networkService;
        return Proxy.newProxyInstance(networkService.getClass().getClassLoader(), networkService.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        Logger.i("network request before...");

        if (objects == null) {
            objects = new Object[1];
        }

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("user", "123456");
        headerMap.put("sign", "xxxxxxx");
        objects[objects.length - 1] = headerMap;

        Object result = method.invoke(networkService, objects);

        Logger.i("network request after...");

        return result;
    }

}
