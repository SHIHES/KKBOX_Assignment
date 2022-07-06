package com.example.android.android_kkbox_assignment.logic.network

import com.example.android.android_kkbox_assignment.logic.model.Result
import com.example.android.android_kkbox_assignment.logic.model.Rss

interface PodcastDataSource {
    
    suspend fun getRSSData(): Result<Rss>
    
}