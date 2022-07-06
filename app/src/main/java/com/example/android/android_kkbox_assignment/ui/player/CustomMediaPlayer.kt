package com.example.android.android_kkbox_assignment.ui.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.*

class CustomMediaPlayer(
//    val callBack: () -> Unit,
    val source: String) {
    
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var TAG = "CustomMediaPlayer"
    var isPlayingMusic = false
    
    private var mediaPlayerJob = Job()
    
    private val coroutineScope = CoroutineScope(mediaPlayerJob + Dispatchers.IO)
    
    
    fun playOrPauseMusic() {
        if (mediaPlayer.isPlaying) {
            isPlayingMusic = mediaPlayer.isPlaying

            mediaPlayer.pause()
        } else {
            isPlayingMusic = mediaPlayer.isPlaying
            mediaPlayer.start()
        }

    }
    
    fun stopMusic() {
        mediaPlayer.seekTo(0)
        mediaPlayer.stop()
        mediaPlayer.prepare()
    }
    
    fun seekToProgress(progress: Int) {
        mediaPlayer.seekTo(progress)
    }
    
    fun getDuration(): Int{
        return mediaPlayer.duration
    }
    
    fun getCurrentEpisodeProgress(): Int {
        return mediaPlayer.currentPosition
    }
    
    fun playForwardEpisode(){
    
    }
    
    fun playBackwardEpisode(){
    
    }
    
    fun releaseMediaPlayer(){
        mediaPlayer.release()
        coroutineScope.cancel()
    }
    
    fun initialMediaPlayer(){

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
            } catch (e: Exception){
                Log.d(TAG, "cannot find sound resource exception = ${e.message}")
            }
        
        

        mediaPlayer.setOnCompletionListener {
//            callBack.invoke()
        }
    
        mediaPlayer.setOnPreparedListener {
            playOrPauseMusic()
        }
    }
    

    
}