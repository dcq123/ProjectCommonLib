package cn.soft.qing.apptemplate.di.component;

import cn.soft.qing.apptemplate.di.module.ActivityModule;
import cn.soft.qing.apptemplate.di.scope.PerActivity;
import cn.soft.qing.apptemplate.ui.business.home.HomeActivity;
import dagger.Subcomponent;

/**
 * 该
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

}
