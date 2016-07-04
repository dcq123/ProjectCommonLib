package cn.qing.soft.networklib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dcq on 2016/4/20 0020.
 * <p>
 * 创建Retrofit服务的生成类
 */
public class RetrofitServiceGenerator {

    public static boolean isDebug = true;

    /**
     * 创建Retrofit的Service
     *
     * @param baseUrl
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T createService(String baseUrl, Class<T> serviceClass) {
        return createService(baseUrl, serviceClass, false, null);
    }

    /**
     * 创建Retrofit service,在请求中添加公用的header头
     *
     * @param baseUrl
     * @param serviceClass
     * @param headerMap
     * @param <T>
     * @return
     */
    public static <T> T createServiceByHeader(String baseUrl, Class<T> serviceClass, Map<String, String> headerMap) {
        return createService(baseUrl, serviceClass, false, headerMap);
    }

    /**
     * 创建RXJava类型的Retrofit Service
     * @param baseUrl
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T createRxService(String baseUrl, Class<T> serviceClass) {
        return createService(baseUrl, serviceClass, true, null);
    }

    /**
     * 创建RXJava类型的Retrofit Service ,可在请求中设置公用的Header头
     * @param baseUrl
     * @param serviceClass
     * @param headerMap
     * @param <T>
     * @return
     */
    public static <T> T createRxServiceByHeader(String baseUrl, Class<T> serviceClass, Map<String, String> headerMap) {
        return createService(baseUrl, serviceClass, true, headerMap);
    }

    public static <T> T createService(String baseUrl, Class<T> serviceClass, boolean isUseRx, final Map<String, String> headerMap) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        if (headerMap != null && headerMap.size() > 0) {

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

        // 添加日志拦截器
        if (isDebug) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }

        Retrofit.Builder builder =
                new Retrofit
                        .Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create());
        if (isUseRx) {
            builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        }
        OkHttpClient okHttpClient = httpClientBuilder.build();
        Retrofit retrofit = builder.client(okHttpClient).build();
        return retrofit.create(serviceClass);
    }

}
