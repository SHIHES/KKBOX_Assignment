package com.example.android.android_kkbox_assignment.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.android_kkbox_assignment.logic.network.PodcastDataSource
import com.example.android.android_kkbox_assignment.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val repository: PodcastDataSource
) : ViewModelProvider.NewInstanceFactory(){
    
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel(repository)
                
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        
    } as T
}