package cn.soft.qing.apptemplate.di.component;

import cn.soft.qing.apptemplate.di.module.ActivityModule;
import cn.soft.qing.apptemplate.di.module.FragmentModule;
import cn.soft.qing.apptemplate.di.scope.PerActivity;
import cn.soft.qing.apptemplate.ui.business.home.HomeActivity;
import dagger.Subcomponent;

/**
 * 该Component用来配置项目中使用到多所有Activity的注入,必须是某个确切使用到依赖注入的Activity
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);

}
