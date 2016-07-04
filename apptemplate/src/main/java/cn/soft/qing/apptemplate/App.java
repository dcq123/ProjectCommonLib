package cn.soft.qing.apptemplate;

import android.app.Application;
import android.content.Context;

import cn.soft.qing.apptemplate.di.component.ApplicationComponent;
import cn.soft.qing.apptemplate.di.component.DaggerApplicationComponent;
import cn.soft.qing.apptemplate.di.module.ApplicationModule;


public class App extends Application {

    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent
                    .builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return applicationComponent;
    }
}
