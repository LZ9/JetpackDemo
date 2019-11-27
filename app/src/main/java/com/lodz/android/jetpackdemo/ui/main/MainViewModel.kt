package com.lodz.android.jetpackdemo.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.lodz.android.corekt.anko.toastShort
import com.lodz.android.jetpackdemo.api.ApiRepository
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.bean.weather.WeatherBean
import com.lodz.android.jetpackdemo.db.AreaDao
import com.lodz.android.jetpackdemo.ui.base.BaseViewModel
import com.lodz.android.jetpackdemo.utils.runOnMainCatch
import com.lodz.android.jetpackdemo.utils.runOnSuspendIOCatchPg

class MainViewModel : BaseViewModel() {

    private val mAreaDao = AreaDao()

    var isAreaSelected = MutableLiveData<Boolean>()

    var mData = MutableLiveData<WeatherBean?>()

    fun getTitleName(): String {
        return mAreaDao.getCountyBean()?.getAreaName() ?: ""
    }

    fun requestData(context: Context, bean: CountyBean? = null) {
        val daoBean = bean ?: mAreaDao.getCountyBean()
        if (daoBean == null) {
            isAreaSelected.value = true
            return
        }
        getWeatherDataPg(context, daoBean)
    }

    private fun getWeatherDataPg(context: Context, bean: CountyBean) {
        runOnSuspendIOCatchPg(context, action = {
            val data = ApiRepository.get().getWeather(bean.getAreaId()).HeWeather?.get(0) ?: return@runOnSuspendIOCatchPg
            mAreaDao.saveCountyBean(bean)
            runOnMainCatch({
                mData.value = data
            })
        }, error = { e ->
            mData.value = null
            context.toastShort(e.message.toString())
        })
    }

}