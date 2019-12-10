package tv.zhengzai.test

import android.util.Log
import com.orhanobut.logger.Logger

/**
 * Created by hujiachen on 2018/3/13.
 * Email:hujiachen@zhengzai.tv
 * Description:格式化的和普通的log展示
 */
object LoggerUtils {

    var isShow = BuildConfig.DEBUG


    fun <T> loggerE(msg: T) {
        if (isShow)
            Logger.e(msg.toString())
    }

    fun <T> loggerD(msg: T) {
        if (isShow)
            Logger.d(msg.toString())
    }

    fun <T> loggerW(msg: T) {
        if (isShow)
            Logger.w(msg.toString())
    }

    fun <T> loggerV(msg: T) {
        if (isShow)
            Logger.v(msg.toString())
    }

    fun <T> loggerI(msg: T) {
        if (isShow)
            Logger.i(msg.toString())
    }

    fun <T> loggerWtf(msg: T) {
        if (isShow)
            Logger.wtf(msg.toString())
    }

    fun <T> loggerJson(msg: T) {
        if (isShow)
            Logger.json(msg.toString())
    }

    fun <T> loggerXml(msg: T) {
        if (isShow)
            Logger.xml(msg.toString())
    }

    fun <T> logE(tag: String, msg: T) {
        if (isShow)
            Log.e(tag, msg.toString())
    }

    fun <T> logD(tag: String, msg: T) {
        if (isShow)
            Log.d(tag, msg.toString())
    }

    fun <T> logW(tag: String, msg: T) {
        if (isShow)
            Log.w(tag, msg.toString())
    }

    fun <T> logV(tag: String, msg: T) {
        if (isShow)
            Log.v(tag, msg.toString())
    }

    fun <T> logI(tag: String, msg: T) {
        if (isShow)
            Log.i(tag, msg.toString())
    }

    fun loggerJson(json: String) {
        Logger.json(json)
    }

}