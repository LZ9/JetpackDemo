package com.lodz.android.jetpackdemo.utils

import android.content.Context
import com.lodz.android.corekt.anko.getMetaData
import com.lodz.android.corekt.log.PrintLog
import com.lodz.android.pandora.base.application.BaseApplication
import com.lodz.android.pandora.utils.progress.ProgressDialogHelper
import kotlinx.coroutines.*

/**
 *
 * @author zhouL
 * @date 2019/11/20
 */
/** 主线程执行 */
fun runOnMain(action: () -> Unit): Job = GlobalScope.launch(Dispatchers.Main) { action() }

/** 主线程执行捕获异常 */
@JvmOverloads
fun runOnMainCatch(action: () -> Unit, error: (e: Exception) -> Unit = {}): Job =
        GlobalScope.launch(Dispatchers.Main) {
            try {
                action()
            } catch (e: Exception) {
                e.printStackTrace()
                error(e)
            }
        }

/** 主线程延迟[timeMillis]毫秒执行 */
fun runOnMainDelay(timeMillis: Long, action: () -> Unit): Job =
        GlobalScope.launch(Dispatchers.IO) {
            delay(timeMillis)
            GlobalScope.launch(Dispatchers.Main) {
                action()
            }
        }

/** 异步线程执行 */
fun runOnIO(action: () -> Unit): Job = GlobalScope.launch(Dispatchers.IO) { action() }

/** 异步线程执行捕获异常 */
@JvmOverloads
fun runOnIOCatch(action: () -> Unit, error: (e: Exception) -> Unit = {}): Job =
        GlobalScope.launch(Dispatchers.IO) {
            try {
                action()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnMain { error(e) }
            }
        }

/** 异步线程执行挂起函数 */
fun runOnSuspendIO(action: suspend () -> Unit): Job = GlobalScope.launch(Dispatchers.IO) { action() }

/** 异步线程执行挂起函数捕获异常 */
@JvmOverloads
fun runOnSuspendIOCatch(action: suspend () -> Unit, error: (e: Exception) -> Unit = {}): Job =
        GlobalScope.launch(Dispatchers.IO) {
            try {
                action()
            } catch (e: Exception) {
                e.printStackTrace()
                runOnMain { error(e) }
            }
        }

fun runOnSuspendIOCatchPg(context: Context, msg: String = "", cancelable: Boolean = true, canceledOnTouchOutside: Boolean = false,
                          action: suspend () -> Unit,
                          error: (e: Exception) -> Unit = {},
                          pgCancel: () -> Unit = {}) {
    val progressDialog = ProgressDialogHelper.get()
            .setCanceledOnTouchOutside(canceledOnTouchOutside)
            .setCancelable(cancelable)
            .setMsg(msg)
            .create(context)

    runOnMainCatch({ progressDialog.show() })

    val job = GlobalScope.launch(Dispatchers.IO) {
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace()
            printTagLog(e)
            if (e !is CancellationException){
                runOnMain { error(e) }
            }
        }finally {
            runOnMainCatch({ progressDialog.dismiss() })
        }
    }

    progressDialog.setOnCancelListener {
        job.cancel()
        runOnMainCatch({
            progressDialog.dismiss()
            pgCancel()
        })
    }
}

/** 打印标签日志 */
private fun printTagLog(t: Throwable) {
    val app = BaseApplication.get() ?: return
    val tag = app.getMetaData("error_tag")
    if (tag != null && tag is String) {
        if (tag.isNotEmpty()) {
            PrintLog.e(tag, t.toString(), t)
        }
    }
}