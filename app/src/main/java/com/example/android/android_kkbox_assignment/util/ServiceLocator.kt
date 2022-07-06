package com.example.android.android_kkbox_assignment.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.android.android_kkbox_assignment.logic.network.DefaultRepository
import com.example.android.android_kkbox_assignment.logic.network.PodcastDataSource
import com.example.android.android_kkbox_assignment.logic.network.PodcastRemoteDataSource

object ServiceLocator {
    
    @Volatile
    var repository: PodcastDataSource? = null
    @VisibleForTesting set
    
    fun provideTasksRepository(): PodcastDataSource {
        synchronized(this) {
            return repository
                ?: createRepository()
        }
    }
    
    private fun createRepository(): PodcastDataSource {
        return DefaultRepository(
            PodcastRemoteDataSource
        )
    }
}