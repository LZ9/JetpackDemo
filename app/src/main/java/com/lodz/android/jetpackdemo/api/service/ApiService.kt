package com.lodz.android.jetpackdemo.api.service

import com.lodz.android.jetpackdemo.bean.area.City
import com.lodz.android.jetpackdemo.bean.area.County
import com.lodz.android.jetpackdemo.bean.area.Province
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
    fun getProvinces(): Deferred<MutableList<Province>>

    /** 获取省份对应的城市列表 */
    @GET("api/china/{provinceId}")
    fun getCities(@Path("provinceId") provinceId: Int): Deferred<MutableList<City>>

    /** 获取城市对应的区列表 */
    @GET("api/china/{provinceId}/{cityId}")
    fun getCounties(@Path("provinceId") provinceId: Int, @Path("cityId") cityId: Int): Deferred<MutableList<County>>

}