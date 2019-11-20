package com.lodz.android.jetpackdemo.bean.area

import org.litepal.crud.LitePalSupport

class CountyBean : LitePalSupport(), AreaBean {

    var weather_id: String? = null

    var name: String? = null
    override fun getAreaId(): String = weather_id ?: ""

    override fun getAreaName(): String = name ?: ""

}