package com.lodz.android.jetpackdemo.ui.area

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.api.ApiRepository
import com.lodz.android.jetpackdemo.bean.area.AreaBean
import com.lodz.android.jetpackdemo.utils.runOnMain
import com.lodz.android.jetpackdemo.utils.runOnSuspendIOCatch
import com.lodz.android.pandora.widget.dialog.BaseBottomDialog

/**
 * 选择地区
 * @author zhouL
 * @date 2019/11/19
 */
class ChooseAreaDialog(context: Context) : BaseBottomDialog(context ){

    /** 确定按钮 */
    private val mConfirmBtn by bindView<MaterialButton>(R.id.confirm_btn)
    /** 省列表 */
    private val mProvinceRv by bindView<RecyclerView>(R.id.province_rv)
    private lateinit var mProvinceAdapter: AreaListAdapter
    private var mProvinceList: List<AreaBean>? = null
    /** 城市列表 */
    private val mCityRv by bindView<RecyclerView>(R.id.city_rv)
    private lateinit var mCityAdapter :AreaListAdapter
    private var mCityList: List<AreaBean>? = null
    /** 地区列表 */
    private val mCountyRv by bindView<RecyclerView>(R.id.county_rv)
    private lateinit var mCountyAdapter :AreaListAdapter
    private var mCountyList: List<AreaBean>? = null

    override fun getLayoutId(): Int = R.layout.dialog_choose_area

    override fun findViews() {
        super.findViews()
        initProvinceRv()
        initCityRv()
        initCountyRv()
    }

    private fun initProvinceRv() {
        mProvinceAdapter = AreaListAdapter(getContext())
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        mProvinceRv.layoutManager = layoutManager
        mProvinceAdapter.onAttachedToRecyclerView(mProvinceRv)// 如果使用网格布局请设置此方法
        mProvinceRv.setHasFixedSize(true)
        mProvinceRv.adapter = mProvinceAdapter
    }

    private fun initCityRv() {
        mCityAdapter = AreaListAdapter(getContext())
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        mCityRv.layoutManager = layoutManager
        mCityAdapter.onAttachedToRecyclerView(mCityRv)// 如果使用网格布局请设置此方法
        mCityRv.setHasFixedSize(true)
        mCityRv.adapter = mCityAdapter
    }

    private fun initCountyRv() {
        mCountyAdapter = AreaListAdapter(getContext())
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        mCountyRv.layoutManager = layoutManager
        mCountyAdapter.onAttachedToRecyclerView(mCountyRv)// 如果使用网格布局请设置此方法
        mCountyRv.setHasFixedSize(true)
        mCountyRv.adapter = mCountyAdapter
    }

    override fun setListeners() {
        super.setListeners()

        mConfirmBtn.setOnClickListener {

        }

        mProvinceAdapter.setOnItemClickListener { viewHolder, item, position ->

        }

        mCityAdapter.setOnItemClickListener { viewHolder, item, position ->

        }

        mCountyAdapter.setOnItemClickListener { viewHolder, item, position ->

        }
    }

    override fun initData() {
        super.initData()
        requestProvinceList()
    }

    private fun requestProvinceList() {
        runOnSuspendIOCatch({
            mProvinceList = ApiRepository.get().getProviceList()
            runOnMain {
                mProvinceAdapter.setData(mProvinceList?.toMutableList())
                mProvinceAdapter.notifyDataSetChanged()
            }
        }, {e: Exception ->

        })
    }

}