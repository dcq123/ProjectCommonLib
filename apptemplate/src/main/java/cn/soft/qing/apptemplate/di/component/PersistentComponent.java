package cn.soft.qing.apptemplate.di.component;

import cn.soft.qing.apptemplate.di.scope.PerPersistent;
import cn.soft.qing.apptemplate.di.module.ActivityModule;
import dagger.Component;

@PerPersistent
@Component(dependencies = ApplicationComponent.class)
public interface PersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

}