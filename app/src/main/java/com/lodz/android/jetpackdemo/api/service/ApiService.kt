package com.lodz.android.jetpackdemo.api.service

import com.lodz.android.jetpackdemo.bean.area.CityBean
import com.lodz.android.jetpackdemo.bean.area.CountyBean
import com.lodz.android.jetpackdemo.bean.area.ProvinceBean
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 接口
 * @author zhouL
 * @date 2019/11/19
 */
interface ApiService {

    /** 获取省份列表 */
    @GET("api/china")
    fun getProvinces(): Deferred<MutableList<ProvinceBean>>

    /** 获取省份对应的城市列表 */
    @GET("api/china/{provinceId}")
    fun getCities(@Path("provinceId") provinceId: Int): Deferred<MutableList<CityBean>>

    /** 获取城市对应的区列表 */
    @GET("api/china/{provinceId}/{cityId}")
    fun getCounties(@Path("provinceId") provinceId: Int, @Path("cityId") cityId: Int): Deferred<MutableList<CountyBean>>

}