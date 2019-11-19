package com.lodz.android.jetpackdemo

import com.lodz.android.corekt.network.NetworkManager
import com.lodz.android.pandora.base.application.BaseApplication
import org.litepal.LitePal

/**
 * Application
 * Created by zhouL on 2018/6/20.
 */
class App : BaseApplication() {

    companion object {
        @JvmStatic
        fun get(): App = BaseApplication.get() as App
    }

    override fun onStartCreate() {
        NetworkManager.get().init(this)
        LitePal.initialize(this)
    }

    override fun onExit() {
        NetworkManager.get().release(this)
        NetworkManager.get().clearNetworkListener()
    }

}