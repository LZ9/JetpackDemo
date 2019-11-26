package com.lodz.android.jetpackdemo.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.lodz.android.corekt.anko.dp2px
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.api.ApiRepository
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.db.AreaDao
import com.lodz.android.jetpackdemo.ui.area.ChooseAreaDialog
import com.lodz.android.jetpackdemo.utils.runOnMainCatch
import com.lodz.android.jetpackdemo.utils.runOnSuspendIOCatchPg
import com.lodz.android.pandora.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    private val mAreaDao = AreaDao()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun findViews(savedInstanceState: Bundle?) {
        super.findViews(savedInstanceState)
        getTitleBarLayout().needBackButton(false)
        getTitleBarLayout().setTitleName(mAreaDao.getCountyBean()?.getAreaName() ?: "")
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

    override fun initData() {
        super.initData()
        val bean = mAreaDao.getCountyBean()
        if (bean == null) {
            showChooseAreaDialog()
            showStatusCompleted()
            return
        }
        requestData(bean)
    }

    private fun showChooseAreaDialog() {
        val dialog = ChooseAreaDialog(getContext())
        dialog.setOnSelectedListener { bean ->
            if (bean is CountyBean){
                requestData(bean)
            }
        }
        dialog.show()
    }

    private fun requestData(bean: CountyBean) {
        runOnSuspendIOCatchPg(getContext(), action = {
            val data = ApiRepository.get().getWeather(bean.getAreaId()).HeWeather?.get(0) ?: return@runOnSuspendIOCatchPg
            mAreaDao.saveCountyBean(bean)
            runOnMainCatch({
                getTitleBarLayout().setTitleName(data.basic?.location ?: "")
                showStatusCompleted()
            })
        }, error = { e ->
            toastShort(e.message.toString())
        })
    }
}