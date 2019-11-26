package com.lodz.android.jetpackdemo.api

import com.lodz.android.jetpackdemo.bean.area.CityBean
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.bean.area.ProvinceBean
import com.lodz.android.jetpackdemo.bean.weather.WeatherResponseBean
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * 接口
 * @author zhouL
 * @date 2019/11/19
 */
interface ApiService {

    /** 获取省份列表 */
    @GET("api/china")
    fun getProvincesAsync(): Deferred<MutableList<ProvinceBean>>

    /** 获取省份对应的城市列表 */
    @GET("api/china/{provinceId}")
    fun getCitiesAsync(@Path("provinceId") provinceId: String): Deferred<MutableList<CityBean>>

    /** 获取城市对应的区列表 */
    @GET("api/china/{provinceId}/{cityId}")
    fun getCountiesAsync(@Path("provinceId") provinceId: String, @Path("cityId") cityId: String): Deferred<MutableList<CountyBean>>

    @GET("api/weather")
    fun getWeatherAsync(@Query("cityid") cityid: String): Deferred<WeatherResponseBean>
}