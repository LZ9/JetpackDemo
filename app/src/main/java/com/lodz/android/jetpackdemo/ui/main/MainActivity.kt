package com.lodz.android.jetpackdemo.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lodz.android.corekt.anko.dp2px
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.corekt.log.PrintLog
import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.ui.area.ChooseAreaDialog
import com.lodz.android.pandora.base.activity.BaseActivity

class MainActivity : BaseActivity() {


    private val mViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().needBackButton(false)
        getTitleBarLayout().setTitleName(mViewModel.getTitleName())
        getTitleBarLayout().setTitleTextColorInt(Color.WHITE)
        getTitleBarLayout().setBackgroundColor(getColorCompat(R.color.colorPrimary))
        getTitleBarLayout().addExpandView(getAreaView())
    }

    private fun getAreaView(): View {
        val tv = TextView(getContext())
        tv.setText(R.string.choose_area)
        tv.setPadding(dp2px(6), 0, dp2px(6), 0)
        tv.setTextColor(Color.WHITE)
        tv.setOnClickListener {
            showChooseAreaDialog()
        }
        return tv
    }

    override fun setListeners() {
        super.setListeners()
        mViewModel.isAreaSelected.observe(this, Observer { value ->
            if (value){
                showChooseAreaDialog()
                showStatusCompleted()
            }
        })

        mViewModel.mData.observe(this, Observer {value->
            if (value == null){
                showStatusNoData()
            }else{
                getTitleBarLayout().setTitleName(mViewModel.getTitleName())
                PrintLog.dS("testtag", value.toString())
                showStatusCompleted()
            }
        })
    }

    override fun initData() {
        super.initData()
        mViewModel.requestData()
    }

    private fun showChooseAreaDialog() {
        val dialog = ChooseAreaDialog(getContext())
        dialog.setOnSelectedListener { bean ->
            if (bean is CountyBean){
                mViewModel.requestData(bean)
            }
        }
        dialog.show()
    }


}