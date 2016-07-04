package cn.qing.soft.networklib.rx;

import android.content.Context;

import cn.qing.soft.networklib.rx.progress.ProgressCancelListener;
import cn.qing.soft.networklib.rx.progress.ProgressDialogHandler;
import rx.Subscriber;

/**
 * Created by dcq on 2016/5/4 0004.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnNextListener<T> mNextListener;
    private ProgressDialogHandler handler;

    public ProgressSubscriber(SubscriberOnNextListener<T> nextListener, Context context) {
        mNextListener = nextListener;
        handler = new ProgressDialogHandler(context, this, true);
    }

    private void showProgress() {
        if (handler != null) {
            handler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgress() {
        if (handler != null) {
            handler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onStart() {

        showProgress();
    }

    @Override
    public void onCompleted() {

        dismissProgress();
        System.out.println("------------请求完成-----------");
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("------------请求失败-----------" + e.getMessage());
        dismissProgress();
    }

    @Override
    public void onNext(T t) {
        if (mNextListener != null) {
            mNextListener.onNext(t);
        }
    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}
