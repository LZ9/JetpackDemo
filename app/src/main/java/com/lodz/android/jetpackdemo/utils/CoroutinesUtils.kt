package com.lodz.android.jetpackdemo.utils

import kotlinx.coroutines.*

/**
 *
 * @author zhouL
 * @date 2019/11/20
 */
/** 主线程执行 */
fun runOnMain(block: () -> Unit): Job = GlobalScope.launch(Dispatchers.Main) { block() }

fun runOnMainCatch(block: () -> Unit, failure: (e: Exception) -> Unit): Job =
        GlobalScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                failure(e)
            }
        }

/** 主线程延迟[timeMillis]毫秒执行 */
fun runOnMainDelay(timeMillis: Long, block: () -> Unit): Job =
        GlobalScope.launch(Dispatchers.IO) {
            delay(timeMillis)
            GlobalScope.launch(Dispatchers.Main) {
                block()
            }
        }



/** 异步线程执行 */
fun runOnIO(block: () -> Unit): Job = GlobalScope.launch(Dispatchers.IO) { block() }

fun runOnIOCatch(block: () -> Unit, failure: (e: Exception) -> Unit): Job =
        GlobalScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                failure(e)
            }
        }

/** 异步线程执行挂起函数 */
fun runOnSuspendIO(block: suspend () -> Unit): Job = GlobalScope.launch(Dispatchers.IO) { block() }

fun runOnSuspendIOCatch(block: suspend () -> Unit, failure: (e: Exception) -> Unit): Job =
        GlobalScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                failure(e)
            }
        }
