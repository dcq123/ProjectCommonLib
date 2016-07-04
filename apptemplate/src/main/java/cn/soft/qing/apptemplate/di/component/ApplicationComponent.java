package cn.soft.qing.apptemplate.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import cn.soft.qing.apptemplate.data.DataManager;
import cn.soft.qing.apptemplate.data.local.DataBaseHelper;
import cn.soft.qing.apptemplate.data.local.PreferencesHelper;
import cn.soft.qing.apptemplate.di.module.ApplicationModule;
import cn.soft.qing.apptemplate.di.scope.ApplicationContext;
import cn.soft.qing.apptemplate.utils.RxEventBus;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    PreferencesHelper preferencesHelper();

    DataBaseHelper databaseHelper();

    DataManager dataManager();

    RxEventBus eventBus();

}
