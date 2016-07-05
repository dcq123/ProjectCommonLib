package cn.soft.qing.apptemplate.di.component;

import cn.soft.qing.apptemplate.di.module.FragmentModule;
import cn.soft.qing.apptemplate.di.scope.PerFragment;
import cn.soft.qing.apptemplate.ui.business.my.MyFragment;
import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MyFragment fragment);

}
