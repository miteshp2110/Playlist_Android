package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class UserHomeViewModel(private val token : String) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(token)

    var _trendingSongs:LiveData<GetTrendingSongs?> = userRepository.trendingSongs

    init {
        getTrendingSongs()
    }

    private fun getTrendingSongs(){
        viewModelScope.launch {
            userRepository.getTrendingSongs()
        }
    }

}