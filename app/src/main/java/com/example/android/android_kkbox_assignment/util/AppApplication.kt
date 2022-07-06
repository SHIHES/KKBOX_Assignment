package com.example.android.android_kkbox_assignment.util

import android.app.Application
import com.example.android.android_kkbox_assignment.logic.network.PodcastDataSource
import kotlin.properties.Delegates

class AppApplication : Application() {
    
    val podcastRepository: PodcastDataSource
        get() = ServiceLocator.provideTasksRepository()
    
    companion object {
        var instance: AppApplication by Delegates.notNull()
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}