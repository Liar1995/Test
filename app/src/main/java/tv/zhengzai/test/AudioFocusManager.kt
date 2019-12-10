package tv.zhengzai.test

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build
import android.util.Log

/**
 * Created by sunmeng on 2019-12-02.
 * Email:sunmeng995@gmail.com
 * Description:
 */
object AudioFocusManager : AudioManager.OnAudioFocusChangeListener {

    private var mAudioManager: AudioManager? = null
    private var mAudioFocusRequest: AudioFocusRequest? = null
    private var mAudioListener: AudioListener? = null
    /**
     * 请求音频焦点 设置监听
     */
    fun requestAudioFocus(mContext: Context): Int {
        if (mAudioManager == null) {
            mAudioManager = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }

        var requestFocusResult = 0
        if (mAudioManager != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                //下面两个常量参数试过很多都无效，最终反编译了其他app才搞定，汗~
                requestFocusResult = mAudioManager!!.requestAudioFocus(this,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                Log.d(TAG, "requestAudioFocus: SDK_INT < 26 =$requestFocusResult")
            } else {
                if (mAudioFocusRequest == null) {
                    mAudioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                            .setAudioAttributes(AudioAttributes.Builder()
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                                    .build())
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(this)
                            .build()
                }
                requestFocusResult = mAudioManager!!.requestAudioFocus(mAudioFocusRequest!!)
                Log.d(TAG, "requestAudioFocus: SDK_INT >= 26 =$requestFocusResult")
            }
        }
        return requestFocusResult
    }

    override fun onAudioFocusChange(focusChange: Int) {
        Log.d(TAG, "onAudioFocusChange: $focusChange")
        mAudioListener?.let {
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> {
                    Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_GAIN")
                    it.play()
                }
                AudioManager.AUDIOFOCUS_LOSS -> {
                    Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_LOSS")
                    it.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                    Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_LOSS_TRANSIENT")
                    it.pause()
                }
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                    Log.d(TAG, "onAudioFocusChange: AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK")
                    it.pause()
                }
                else -> {
                }
            }
        }
    }

    /**
     * 暂停、播放完成或退到后台释放音频焦点
     * 应该先请求音频焦点
     */
    fun releaseAudioFocus(mContext: Context): Int {
        if (mAudioManager == null) {
            mAudioManager = mContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        }

        var abandonFocusResult = 0
        if (mAudioManager != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                abandonFocusResult = mAudioManager!!.abandonAudioFocus(this)
                Log.d(TAG, "releaseAudioFocus: SDK_INT < 26 =$abandonFocusResult")
            } else {
                if (mAudioFocusRequest != null) {
                    abandonFocusResult = mAudioManager!!.abandonAudioFocusRequest(mAudioFocusRequest!!)
                    Log.d(TAG, "releaseAudioFocus: SDK_INT >= 26 =$abandonFocusResult")
                }
            }
        }
        return abandonFocusResult
    }

    fun setOnAudioFocusChangeListener(audioListener: AudioListener) {
        mAudioListener = audioListener
    }

    interface AudioListener {
        fun play()

        fun pause()
    }

    private val TAG = "AudioFocusManager"

}
