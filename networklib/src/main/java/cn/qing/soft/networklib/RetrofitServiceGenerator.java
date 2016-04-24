package cn.qing.soft.networklib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dcq on 2016/4/20 0020.
 * <p/>
 * 创建Retrofit服务的工厂类
 */
public class RetrofitServiceGenerator {

    private static Retrofit.Builder builder = null;
    private static boolean isInit = false;

    private static Map<Class, Object> serviceMap = new HashMap<>();


    public static void init(String baseUrl, boolean isUseRx) {
        builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create());
        if (isUseRx) {
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        isInit = true;
    }

    public static void checkIsInit() {
        if (!isInit) {
            throw new IllegalStateException("RetrofitServiceGenerator must init...");
        }
    }


    /**
     * 根据指定的Service类型，创建对应的Service
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> serviceClass) {
        return createService(serviceClass, null);
    }


    /**
     * 根据指定的Service类型，创建对应的Service，可以在请求中设置Header数据
     *
     * @param serviceClass
     * @param headerMap
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> serviceClass, final Map<String, String> headerMap) {

        checkIsInit();

        if (serviceMap.containsKey(serviceClass)) {
            System.out.println("从缓存中获取到service实例");
            return (T) serviceMap.get(serviceClass);
        }

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (headerMap != null && headerMap.size() > 0) {
            httpClientBuilder.interceptors().clear();

            httpClientBuilder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder();
                    // 将需要放入Http header中的字段添加到header中
                    for (String key : headerMap.keySet()) {
                        builder.addHeader(key, headerMap.get(key));
                    }
                    builder.method(original.method(), original.body());

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            });
        }


        Retrofit retrofit = builder.client(httpClientBuilder.build()).build();

        T service = retrofit.create(serviceClass);
        serviceMap.put(serviceClass, service);
        return service;
    }

}