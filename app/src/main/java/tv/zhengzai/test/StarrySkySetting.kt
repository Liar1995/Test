package tv.zhengzai.test

import android.Manifest
import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.lzx.starrysky.StarrySkyBuilder
import com.lzx.starrysky.StarrySkyConfig
import com.lzx.starrysky.delayaction.Valid
import com.lzx.starrysky.playback.offline.StarrySkyCacheManager
import com.lzx.starrysky.playback.player.Playback
import com.lzx.starrysky.provider.SongInfo
import com.lzx.starrysky.registry.StarrySkyRegistry
import com.qw.soul.permission.SoulPermission
import com.qw.soul.permission.bean.Permission
import com.qw.soul.permission.bean.Permissions
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener

/**
 * Created by sunmeng on 2019-11-01.
 * Email:sunmeng995@gmail.com
 * Description:
 */
class StarrySkySetting {

    class IstvConfig : StarrySkyConfig() {
        override fun applyOptions(context: Context, builder: StarrySkyBuilder) {
            super.applyOptions(context, builder)
            builder.setOpenNotification(true)
        }

        override fun applyStarrySkyRegistry(context: Context, registry: StarrySkyRegistry?) {
            super.applyStarrySkyRegistry(context, registry)
            registry?.let {
                it.appendValidRegistry(RequestSongInfoValid(context))
//                it.registryImageLoader(GlideLoader())
                it.registryPlayback(getISTVPlayback(context))
            }
        }

        private fun getISTVPlayback(context: Context): Playback {
            val cacheManager = StarrySkyCacheManager(context, false, "", cacheFactory)
            return ISTVPlayback(context, cacheManager)
        }
    }

    class RequestSongInfoValid internal constructor(private val mContext: Context) : Valid {
//        override fun doValid(songInfo: SongInfo, callback: Valid.ValidCallback) {
        override fun doValid(songInfo: SongInfo?, callback: Valid.ValidCallback) {
            SoulPermission.getInstance().checkAndRequestPermissions(
                    Permissions.build(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    object : CheckRequestPermissionsListener {
                        override fun onAllPermissionOk(allPermissions: Array<Permission>) {
                            LoggerUtils.loggerD("播放歌曲之前===请求音频焦点")
                            AudioFocusManager.requestAudioFocus(mContext)
//                            if (TextUtils.isEmpty(songInfo.songUrl)) {
//                                mMusicRequest.getSongInfoDetail(songInfo.songId, { songUrl ->
//                                    songInfo.songUrl = songUrl //给songInfo设置Url
//                                    callback.finishValid()
//                                })
//                            } else {
//                                callback.doActionDirect()
//                            }
                        }

                        override fun onPermissionDenied(refusedPermissions: Array<Permission>) {
                            Toast.makeText(mContext, "没有权限，播放失败", Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }

}