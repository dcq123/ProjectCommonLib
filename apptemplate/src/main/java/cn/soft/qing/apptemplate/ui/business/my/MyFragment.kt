package cn.soft.qing.apptemplate.ui.business.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.soft.qing.apptemplate.R
import cn.soft.qing.apptemplate.ui.base.BaseFragment
import cn.soft.qing.apptemplate.utils.DialogHelper
import kotlinx.android.synthetic.main.fragment_my_layout.view.*
import javax.inject.Inject

class MyFragment : BaseFragment() {

    @Inject
    lateinit var dialogHelper: DialogHelper


    lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_my_layout, container, true)

        rootView.showDialog.setOnClickListener {

            dialogHelper.showDialog("ashdfaf")

        }

        return rootView
    }


}