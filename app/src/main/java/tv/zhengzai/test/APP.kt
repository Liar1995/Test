package tv.zhengzai.test

import android.app.Application
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.utils.StarrySkyUtils

/**
 * Created by sunmeng on 2019-12-10.
 * Email:sunmeng995@gmail.com
 * Description:
 */
class APP : Application() {
    override fun onCreate() {
        super.onCreate()
        StarrySky.init(this)
//        StarrySky.init(this, StarrySkySetting.IstvConfig())
        StarrySkyUtils.isDebug = true
    }
}