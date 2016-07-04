package cn.soft.qing.apptemplate.ui.business.home

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast

import cn.soft.qing.apptemplate.R
import cn.soft.qing.apptemplate.data.DataManager
import cn.soft.qing.apptemplate.ui.base.BaseActivity
import kotlinx.android.synthetic.main.content_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeMvpView {

    @Inject lateinit var homePresenter: HomePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent().inject(this)

        setContentView(R.layout.activity_home)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        homePresenter.attachView(this)

        testButton.setOnClickListener {
            homePresenter.loadData()

        }

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener({ view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() })
    }

    override fun loadDataSuccess() {
        Toast.makeText(this, "加载成功", Toast.LENGTH_SHORT).show()
    }

    override fun loadDataFail() {
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show()

    }

}
