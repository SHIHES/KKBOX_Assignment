package com.example.android.android_kkbox_assignment.logic.network

import android.util.Log
import com.example.android.android_kkbox_assignment.R
import com.example.android.android_kkbox_assignment.logic.model.Channel
import com.example.android.android_kkbox_assignment.logic.model.Result
import com.example.android.android_kkbox_assignment.logic.model.Rss
import com.example.android.android_kkbox_assignment.util.Util

object PodcastRemoteDataSource : PodcastDataSource {
    
    const val TAG = "PodcastRemoteDataSource"
    
    override suspend fun getRSSData(): Result<Rss> {
        
        if (!Util.isInternetConnected()){
            return Result.Fail(Util.getString(R.string.internet_not_connected))
        }
        
        return try {
            val callback = EpisodeApi.retrofitService.getRSSData()
            Log.d(TAG, "[${this::class.simpleName}] getRSSData callback=${callback}")
            Result.Success(callback)
            
        } catch (e: Exception){
            Log.d(TAG, "[${this::class.simpleName}] getRSSData exception=${e.message}")
            Result.Error(e)
        }
    }
}