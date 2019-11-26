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
        val list = LitePal.findAll(CountyBean::class.java)
        val daoBean: CountyBean? = if (list.isEmpty()) null else list[0]
        if (daoBean == null) {
            bean.save()
            return
        }
        if (daoBean.weather_id == bean.weather_id) {
            bean.saveOrUpdate()
            return
        }
        LitePal.deleteAll(CountyBean::class.java)
        bean.save()
    }
}