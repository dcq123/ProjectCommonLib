package cn.soft.qing.apptemplate.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import cn.soft.qing.apptemplate.di.scope.ActivityContext
import cn.soft.qing.apptemplate.di.scope.PerActivity
import javax.inject.Inject

@PerActivity
class DialogHelper
@Inject constructor(@ActivityContext var context: Context) {

    var dialog: AlertDialog? = null

    fun showDialog(msg: String, cancelable: Boolean = true): AlertDialog? {
        if (dialog == null) {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("提示:")
            builder.setMessage("加载中,请稍后...")
            builder.setPositiveButton("好的") { dialog, p1 ->
                dialog.dismiss()
            }
            dialog = builder.create()
            dialog?.setCancelable(cancelable)
            dialog?.setCanceledOnTouchOutside(cancelable)
        }
        dialog?.show()
        return dialog
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }


}