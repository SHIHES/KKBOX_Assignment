package com.example.android.android_kkbox_assignment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.android_kkbox_assignment.R
import com.example.android.android_kkbox_assignment.logic.model.Episode
import com.example.android.android_kkbox_assignment.logic.model.LoadApiStatus
import com.example.android.android_kkbox_assignment.logic.model.Result
import com.example.android.android_kkbox_assignment.logic.model.Rss
import com.example.android.android_kkbox_assignment.logic.network.PodcastDataSource
import com.example.android.android_kkbox_assignment.util.Util
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PodcastDataSource) : ViewModel() {
    
    private val coroutineScope = viewModelScope
    
    private val _status = MutableLiveData<LoadApiStatus>()
    
    val status: LiveData<LoadApiStatus>
        get() = _status
    
    private val _error = MutableLiveData<String?>()
    
    val error: LiveData<String?>
        get() = _error
    
    private val _channelData = MutableLiveData<Rss>()
    
    val channelData: LiveData<Rss>
        get() = _channelData
    
    private val _selectedEpisodePosition = MutableLiveData<Int>()
    
    val selectedEpisodePosition: LiveData<Int>
        get() = _selectedEpisodePosition
    
    init {
        getRSSData(true)
    }
    
    
    private fun getRSSData(isInitial: Boolean = false){
        
        coroutineScope.launch {
            
            if (isInitial) _status.value = LoadApiStatus.LOADING
            
            val result = repository.getRSSData()
            
            _channelData.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    if (isInitial) _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    if (isInitial) _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    if (isInitial) _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = Util.getString(R.string.unexpected_error)
                    if (isInitial) _status.value = LoadApiStatus.ERROR
                    null
                }
                
            }
            
        }
    }
    
    fun navigateToEpisode(position: Int){
        _selectedEpisodePosition.value = position
    }
    
    fun onEpisodeNavigated(){
        _selectedEpisodePosition.value = null
    }
}