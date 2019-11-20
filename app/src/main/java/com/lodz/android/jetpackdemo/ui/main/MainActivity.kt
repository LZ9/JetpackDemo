package com.lodz.android.jetpackdemo.ui.main

import com.lodz.android.jetpackdemo.R
import com.lodz.android.jetpackdemo.ui.area.ChooseAreaDialog
import com.lodz.android.pandora.base.activity.AbsActivity

class MainActivity : AbsActivity() {


    override fun getAbsLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        super.initData()
        ChooseAreaDialog(getContext()).show()
    }
}