package cn.soft.qing.apptemplate.ui.business.home

import cn.soft.qing.apptemplate.data.DataManager
import cn.soft.qing.apptemplate.data.model.PicModel
import cn.soft.qing.apptemplate.di.scope.PerPersistent
import cn.soft.qing.apptemplate.ui.base.BasePresenter
import rx.Subscriber
import rx.Subscription
import javax.inject.Inject

@PerPersistent
class HomePresenter @Inject constructor(var dataManager: DataManager)
: BasePresenter<HomeMvpView>() {

    var subscription: Subscription? = null

    override fun detachView() {
        super.detachView()
        subscription?.unsubscribe()
    }

    fun loadData() {
        checkViewAttached()
        subscription = dataManager
                .getPicUrlList("dingchangqing", "asdfasdf")
                .subscribe(object : Subscriber<PicModel>() {
                    override fun onCompleted() {
                        print("请求完成...")
                    }

                    override fun onError(e: Throwable?) {
                        print("请求出错...")
                        mvpView.loadDataFail()
                    }

                    override fun onNext(t: PicModel?) {
                        print(t)
                        mvpView.loadDataSuccess()

                    }

                })


    }

}