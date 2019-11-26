package com.lodz.android.jetpackdemo.bean.weather

import com.lodz.android.pandora.rx.status.ResponseStatus

/**
 * 天气信息类
 * @author zhouL
 * @date 2019/11/25
 */
class WeatherBean : ResponseStatus {
    /** 状态 */
    var status: String? = ""
    /** 描述 */
    var msg: String? = ""
    /** 地区信息数据 */
    var basic: AreaInfoBean? = null
    /** 当前天气 */
    var now: NowBean? = null

    override fun isSuccess(): Boolean = status == "ok"

    override fun valueMsg(): String = msg ?: ""

    override fun valueStatus(): String = status ?: ""
}