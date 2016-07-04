package cn.soft.qing.apptemplate.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cn.qing.soft.networklib.RetrofitServiceGenerator;
import cn.soft.qing.apptemplate.data.remote.NetworkHandler;
import cn.soft.qing.apptemplate.data.remote.NetworkService;
import cn.soft.qing.apptemplate.di.scope.ApplicationContext;
import dagger.Module;
import dagger.Provides;

/**
 * 提供 application-level 的依赖.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    NetworkService provideNetworkService() {
        NetworkService networkService = RetrofitServiceGenerator.createRxService(NetworkService.BASE_URL, NetworkService.class);
        return (NetworkService) new NetworkHandler().bind(networkService);
    }

}
