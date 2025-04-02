package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class UserHomeViewModel(private val token : String) : ViewModel() {
    private val userRepository: UserRepository = UserRepository(token)

    var _trendingSongs:LiveData<GetTrendingSongs?> = userRepository.trendingSongs
    var _topArtist:LiveData<GetAllArtist?> = userRepository.topArtist

    init {
        getTrendingSongs()
        getTopArtist()
    }

    private fun getTrendingSongs(){
        viewModelScope.launch {
            userRepository.getTrendingSongs()
        }
    }

    private fun getTopArtist(){
        viewModelScope.launch {
            userRepository.getTopArtist()
        }
    }

}