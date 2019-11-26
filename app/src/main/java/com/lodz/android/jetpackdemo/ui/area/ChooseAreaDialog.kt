package com.lodz.android.jetpackdemo.ui.area

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.api.ApiRepository
import com.lodz.android.jetpackdemo.bean.area.AreaBean
import com.lodz.android.jetpackdemo.utils.runOnMain
import com.lodz.android.jetpackdemo.utils.runOnSuspendIOCatchPg
import com.lodz.android.pandora.widget.dialog.BaseBottomDialog

/**
 * 选择地区
 * @author zhouL
 * @date 2019/11/19
 */
class ChooseAreaDialog(context: Context) : BaseBottomDialog(context ){

    private val mProvinceRv by bindView<RecyclerView>(R.id.recycler_view)
    private lateinit var mProvinceAdapter: AreaListAdapter
    /** 省列表 */
    private var mProvinceList: List<AreaBean>? = null
    /** 城市列表 */
    private var mCityList: List<AreaBean>? = null
    /** 区列表 */
    private var mCountyList: List<AreaBean>? = null
    /** 省Id */
    private var mProvinceId = ""

    /** 监听器回调 */
    private var mListener: ((bean: AreaBean) -> Unit)? = null

    override fun getLayoutId(): Int = R.layout.dialog_choose_area

    override fun findViews() {
        super.findViews()
        initRecyclerView()
        setCanceledOnTouchOutside(true)
        setCancelable(false)
    }

    /** 初始化RecyclerView */
    private fun initRecyclerView() {
        mProvinceAdapter = AreaListAdapter(getContext())
        val layoutManager = LinearLayoutManager(getContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        mProvinceRv.layoutManager = layoutManager
        mProvinceAdapter.onAttachedToRecyclerView(mProvinceRv)// 如果使用网格布局请设置此方法
        mProvinceRv.setHasFixedSize(true)
        mProvinceRv.adapter = mProvinceAdapter
    }

    override fun setListeners() {
        super.setListeners()

        mProvinceAdapter.setOnItemClickListener { viewHolder, item, position ->
            if (mCityList == null) {// 点击省
                mProvinceId = item.getAreaId()
                requestCityList(mProvinceId)
                return@setOnItemClickListener
            }
            if (mCountyList == null) {//点击市
                requestCountyList(mProvinceId, item.getAreaId())
                return@setOnItemClickListener
            }
            mListener?.invoke(item)//回调选择结果
            dismiss()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (mCountyList != null){// 返回市
            mCountyList = null
            mProvinceAdapter.setData(mCityList?.toMutableList())
            mProvinceAdapter.notifyDataSetChanged()
            return
        }
        if (mCityList != null){// 返回省
            mCityList = null
            mProvinceAdapter.setData(mProvinceList?.toMutableList())
            mProvinceAdapter.notifyDataSetChanged()
            return
        }
        dismiss()
    }

    override fun initData() {
        super.initData()
        requestProvinceList()
    }

    /** 获取省列表 */
    private fun requestProvinceList() {
        runOnSuspendIOCatchPg(context, action = {
            mProvinceList = ApiRepository.get().getProviceList()
            runOnMain {
                mProvinceAdapter.setData(mProvinceList?.toMutableList())
                mProvinceAdapter.notifyDataSetChanged()
            }
        }, error = { e: Exception ->
            context.toastShort(e.message.toString())
        })
    }

    /** 获取市列表 */
    private fun requestCityList(provinceId: String) {
        runOnSuspendIOCatchPg(context, action = {
            mCityList = ApiRepository.get().getCityList(provinceId)
            runOnMain {
                mProvinceAdapter.setData(mCityList?.toMutableList())
                mProvinceAdapter.notifyDataSetChanged()
            }
        }, error = { e: Exception ->
            context.toastShort(e.message.toString())
        })
    }

    /** 获取区列表 */
    private fun requestCountyList(provinceId: String, cityId: String) {
        runOnSuspendIOCatchPg(context, action = {
            mCountyList = ApiRepository.get().getCountyList(provinceId, cityId)
            runOnMain {
                mProvinceAdapter.setData(mCountyList?.toMutableList())
                mProvinceAdapter.notifyDataSetChanged()
            }
        }, error = { e: Exception ->
            context.toastShort(e.message.toString())
        })
    }

    /** 设置选中监听器[listener] */
    fun setOnSelectedListener(listener: ((bean: AreaBean) -> Unit)?) {
        mListener = listener
    }
}