package cn.soft.qing.apptemplate.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import cn.soft.qing.apptemplate.App;
import cn.soft.qing.apptemplate.di.component.ActivityComponent;
import cn.soft.qing.apptemplate.di.component.DaggerPersistentComponent;
import cn.soft.qing.apptemplate.di.component.PersistentComponent;
import cn.soft.qing.apptemplate.di.module.ActivityModule;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, PersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependence(savedInstanceState);
    }

    private void injectDependence(Bundle savedInstanceState) {
        mActivityId = savedInstanceState != null ?
                savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        PersistentComponent persistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            persistentComponent = DaggerPersistentComponent.builder()
                    .applicationComponent(App.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, persistentComponent);
        } else {
            persistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = persistentComponent.activityComponent(new ActivityModule(this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

}
