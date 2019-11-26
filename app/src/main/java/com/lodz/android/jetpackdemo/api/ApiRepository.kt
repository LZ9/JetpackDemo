package com.lodz.android.jetpackdemo.api

import com.lodz.android.jetpackdemo.bean.area.CityBean
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.bean.area.ProvinceBean
import com.lodz.android.jetpackdemo.bean.weather.WeatherResponseBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 接口实现
 * @author zhouL
 * @date 2019/11/20
 */
class ApiRepository private constructor() {

    companion object {
        private val sInstance = ApiRepository()
        fun get(): ApiRepository = sInstance
    }

    suspend fun getProviceList(): MutableList<ProvinceBean> = withContext(Dispatchers.IO) {
        ApiServiceManager.get().create(ApiService::class.java).getProvincesAsync().await()
    }

    suspend fun getCityList(provinceId: String): MutableList<CityBean> = withContext(Dispatchers.IO) {
        ApiServiceManager.get().create(ApiService::class.java).getCitiesAsync(provinceId).await()
    }

    suspend fun getCountyList(provinceId: String, cityId: String): MutableList<CountyBean> = withContext(Dispatchers.IO) {
        ApiServiceManager.get().create(ApiService::class.java).getCountiesAsync(provinceId, cityId).await()
    }

    suspend fun getWeather(cityId: String): WeatherResponseBean = withContext(Dispatchers.IO) {
        ApiServiceManager.get().create(ApiService::class.java).getWeatherAsync(cityId).await()
    }
}