package com.example.android.android_kkbox_assignment.logic.network

import com.example.android.android_kkbox_assignment.logic.model.Result
import com.example.android.android_kkbox_assignment.logic.model.Rss

class DefaultRepository(
    private val remoteDataSource: PodcastDataSource
) : PodcastDataSource {
    
    override suspend fun getRSSData(): Result<Rss> {
        return remoteDataSource.getRSSData()
    }
}