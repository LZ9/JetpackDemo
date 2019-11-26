package com.lodz.android.jetpackdemo.bean.weather

/**
 * 地区信息数据
 * @author zhouL
 * @date 2019/11/25
 */
class AreaInfoBean {
    /** 城市ID */
    var cid: String? = ""
    /** 区 */
    var location: String? = ""
    /** 市 */
    var parent_city: String? = ""
    /** 省 */
    var admin_area: String? = ""
    /** 国家 */
    var cnty: String? = ""
    /** 纬度 */
    var lat: String? = ""
    /** 经度 */
    var lon: String? = ""
}