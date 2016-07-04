package cn.soft.qing.apptemplate.ui.business.home

import cn.soft.qing.apptemplate.ui.base.MvpView

/**
 * Created by dingchangqing on 16/7/4.
 */
interface HomeMvpView : MvpView {

    fun loadDataSuccess();

    fun loadDataFail();
}