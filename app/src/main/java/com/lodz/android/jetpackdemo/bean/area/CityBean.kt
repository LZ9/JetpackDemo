package com.lodz.android.jetpackdemo.bean.area

import org.litepal.crud.LitePalSupport

class CityBean : LitePalSupport(), AreaBean {

    var id: Int? = null
    var name: String? = null

    override fun getAreaId(): String = id?.toString() ?: ""

    override fun getAreaName(): String = name ?: ""

}