package com.example.android.android_kkbox_assignment.ui.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class CustomMediaPlayer(
) {
    
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var TAG = "CustomMediaPlayer"
    var isPlayingMusic = MutableLiveData(true)
    var isPlayingToEnd = MutableLiveData<Boolean>()
    
    
    private var mediaPlayerJob = Job()
    
    private val coroutineScope = CoroutineScope(mediaPlayerJob + Dispatchers.Main)
    
    init {
        mediaPlayer.setOnCompletionListener {
            isPlayingToEnd.value = true
        }
        
        mediaPlayer.setOnPreparedListener {
            playOrPauseMusic()
        }
    }
    
    
    fun playOrPauseMusic() {
        if (mediaPlayer.isPlaying) {
            // playing -> pause
            isPlayingMusic.value = false
            mediaPlayer.pause()
        } else {
            // pause -> playing
            isPlayingMusic.value = true
            mediaPlayer.start()
        }
        
    }

//    fun stopMusic() {
//        mediaPlayer.seekTo(0)
//        mediaPlayer.stop()
//        mediaPlayer.prepare()
//    }
    
    fun seekToProgress(progress: Int) {
        mediaPlayer.seekTo(progress)
    }
    
    fun getDuration(): Int {
        Log.d(TAG, "getDuration ${mediaPlayer.duration}")
        return mediaPlayer.duration
    }
    
    fun getCurrentEpisodeProgress(): Int {
        return mediaPlayer.currentPosition
    }
    
    fun playForwardOrBackwardEpisode(source: String) {
        mediaPlayer.reset()
        initialMediaPlayer(source)
    }
    
    
    fun releaseMediaPlayer() {
        mediaPlayer.release()
        coroutineScope.cancel()
    }
    
    fun initialMediaPlayer(source: String) {
        mediaPlayer.apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
        try {
            mediaPlayer.setDataSource(source)
            mediaPlayer.prepare()
            Log.d(TAG, "find sound resource")
        } catch (e: Exception) {
            Log.d(TAG, "cannot find sound resource exception = ${e.message}")
        }
    }
    
    fun startNextEpisode() {
        isPlayingToEnd.value = false
    }
    
    
}