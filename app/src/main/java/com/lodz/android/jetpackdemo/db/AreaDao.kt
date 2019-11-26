package com.lodz.android.jetpackdemo.db

import com.lodz.android.jetpackdemo.bean.area.CountyBean
import org.litepal.LitePal

/**
 * 地区数据库
 * @author zhouL
 * @date 2019/11/25
 */
class AreaDao {

    /** 获取区数据 */
    fun getCountyBean(): CountyBean? {
        val list = LitePal.findAll(CountyBean::class.java)
        if (list.isNotEmpty()) {
            return list[0]
        }
        return null
    }

    /** 保存区数据 */
    fun saveCountyBean(bean: CountyBean) {
        LitePal.deleteAll(CountyBean::class.java)
        bean.save()
    }
}