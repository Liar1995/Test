package tv.zhengzai.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.common.PlaybackStage
import com.lzx.starrysky.provider.SongInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AudioFocusManager.setOnAudioFocusChangeListener(object : AudioFocusManager.AudioListener {
            override fun play() {
                StarrySky.with().playMusic()
            }

            override fun pause() {
                StarrySky.with().pauseMusic()
                AudioFocusManager.releaseAudioFocus(this@MainActivity)
            }
        })

        txt_hello.setOnClickListener {
            val item = SongInfo()
            item.songId = "111"
            item.songUrl =
                "http://youngblood.modernsky.com/yb_service/public/mp3/20190406/5ca88bb735d8a.mp3"
            StarrySky.with().playMusicByInfo(item)
        }

        //状态监听
        StarrySky.with().playbackState().observe(this, androidx.lifecycle.Observer { playbackStage ->
            if (playbackStage == null) {
                return@Observer
            }
            LoggerUtils.loggerD("PlaybackStage === stage:${playbackStage.getStage()}")
            when (playbackStage.getStage()) {
                PlaybackStage.NONE -> {
                    LoggerUtils.loggerD("PlaybackStage === NONE")
                }
                PlaybackStage.START -> {
                    LoggerUtils.loggerD("PlaybackStage === START")
                }
                PlaybackStage.PAUSE -> {
                    LoggerUtils.loggerD("PlaybackStage === PAUSE")
                }
                PlaybackStage.STOP -> {
                    LoggerUtils.loggerD("PlaybackStage === STOP")
                }
                PlaybackStage.COMPLETION -> {
                    LoggerUtils.loggerD("PlaybackStage === COMPLETION")
                    //播放完成 通知playerNowFragment重置进度条 播放时间
                }
                PlaybackStage.BUFFERING -> {
                    LoggerUtils.loggerD("PlaybackStage === BUFFERING")
//                    toast("缓冲中......")
                    //缓冲
                }
                PlaybackStage.ERROR -> {
                    LoggerUtils.loggerD("PlaybackStage === ERROR")
                    //播放失败
                }
                else -> {
                }
            }
        })

    }
}
