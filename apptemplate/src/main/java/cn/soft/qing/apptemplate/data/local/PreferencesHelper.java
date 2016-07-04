package cn.soft.qing.apptemplate.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.soft.qing.apptemplate.di.scope.ApplicationContext;

/**
 * 本地存储,SP的工具类
 */

@Singleton
public class PreferencesHelper {
    private static final String SP_NAME = "sp_name";
    private SharedPreferences sp;


    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    public void clear() {
        sp.edit().clear().apply();
    }

}
