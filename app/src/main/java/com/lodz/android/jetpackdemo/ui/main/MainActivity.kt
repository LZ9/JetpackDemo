package com.lodz.android.jetpackdemo.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.corekt.anko.dp2px
import com.lodz.android.corekt.anko.getColorCompat
import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.ui.area.ChooseAreaDialog
import com.lodz.android.pandora.base.activity.BaseActivity

class MainActivity : BaseActivity() {

    /** 地区名称 */
    private val mAreaNameTv by bindView<TextView>(R.id.area_name_tv)
    /** 经度 */
    private val mLonTv by bindView<TextView>(R.id.lon_tv)
    /** 纬度 */
    private val mLatTv by bindView<TextView>(R.id.lat_tv)
    /** 温度 */
    private val mTmpTv by bindView<TextView>(R.id.tmp_tv)
    /** 气候 */
    private val mClimateTv by bindView<TextView>(R.id.climate_tv)

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
                mAreaNameTv.text = StringBuilder()
                        .append(value.basic?.cnty ?: "").append(" > ")
                        .append(value.basic?.admin_area ?: "").append(" > ")
                        .append(value.basic?.parent_city ?: "").append(" > ")
                        .append(value.basic?.location ?: "")
                mLonTv.text = StringBuilder().append("经度：").append(value.basic?.lon ?: "")
                mLatTv.text = StringBuilder().append("纬度：").append(value.basic?.lat ?: "")
                mTmpTv.text = StringBuilder().append(value.now?.tmp ?: "").append("℃")
                mClimateTv.text = value.now?.cond_txt ?: ""
                showStatusCompleted()
            }
        })
    }

    override fun initData() {
        super.initData()
        mViewModel.requestData(getContext())
    }

    private fun showChooseAreaDialog() {
        val dialog = ChooseAreaDialog(getContext())
        dialog.setOnSelectedListener { bean ->
            if (bean is CountyBean){
                mViewModel.requestData(getContext(), bean)
            }
        }
        dialog.show()
    }


}