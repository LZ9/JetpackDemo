package com.lodz.android.jetpackdemo.ui.area

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lodz.android.corekt.anko.bindView
import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.bean.area.AreaBean
import com.lodz.android.pandora.widget.rv.recycler.BaseRecyclerViewAdapter

/**
 * 省市区列表适配器
 * @author zhouL
 * @date 2019/11/19
 */
class AreaListAdapter(context: Context) :BaseRecyclerViewAdapter<AreaBean>(context){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            DataViewHolder(getLayoutView(parent, R.layout.rv_item_area_list))

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        val bean = getItem(position)
        if (bean == null || holder !is DataViewHolder) {
            return
        }
        showItem(holder, bean)
    }

    private fun showItem(holder: DataViewHolder, bean: AreaBean) {
        holder.nameTv.text = bean.getAreaName()
    }

    private inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /** 地区名称 */
        val nameTv by bindView<TextView>(R.id.name_tv)
    }
}