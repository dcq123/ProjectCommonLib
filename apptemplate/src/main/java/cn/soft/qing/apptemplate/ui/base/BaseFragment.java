package cn.soft.qing.apptemplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import cn.soft.qing.apptemplate.di.component.FragmentComponent;
import cn.soft.qing.apptemplate.di.module.FragmentModule;


public abstract class BaseFragment extends Fragment {

    private BaseActivity activity;
    private FragmentComponent fragmentComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (BaseActivity) getActivity();
        injectDependence();
    }

    private void injectDependence() {
        if (fragmentComponent == null) {
            fragmentComponent = activity.activityComponent().fragmentComponent(new FragmentModule());
        }
    }

    @Override
    public void onDestroy() {
        fragmentComponent = null;
        super.onDestroy();
    }

    public FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }
}
