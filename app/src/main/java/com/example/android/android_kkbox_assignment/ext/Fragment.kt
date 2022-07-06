package com.example.android.android_kkbox_assignment.ext

import androidx.fragment.app.Fragment
import com.example.android.android_kkbox_assignment.factory.ViewModelFactory
import com.example.android.android_kkbox_assignment.util.AppApplication

fun Fragment.getVmFactory(): ViewModelFactory{
    val repository = (requireContext().applicationContext as AppApplication).podcastRepository
    return  ViewModelFactory(repository)
}