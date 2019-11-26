package com.lodz.android.jetpackdemo.bean.area

class ProvinceBean : AreaBean {

    var id: Int? = null

    var name: String? = null

    override fun getAreaId(): String = id?.toString() ?: ""

    override fun getAreaName(): String = name ?: ""
}