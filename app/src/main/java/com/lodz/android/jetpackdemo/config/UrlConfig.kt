package com.lodz.android.jetpackdemo.config

import androidx.annotation.Keep

/**
 * 地址配置
 * @author zhouL
 * @date 2019/3/22
 */
object UrlConfig {

    @Keep
    private const val RELEASE = "http://guolin.tech/" // 正式地址

    /** 正式环境 */
    const val BASE_URL = RELEASE
}