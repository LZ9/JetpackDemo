package com.lodz.android.jetpackdemo.api

import com.lodz.android.jetpackdemo.bean.area.ProvinceBean
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
        ApiServiceManager.get().create(ApiService::class.java).getProvinces().await()
    }
}