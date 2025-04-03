package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.playlist.GetAllPlaylist
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class UserPlaylistViewModel(private val token : String) : ViewModel() {

    private val userRepository : UserRepository = UserRepository(token)

    val getAllPlaylistResponse : LiveData<GetAllPlaylist?> = userRepository.getAllPlaylistResult




    init {
        getAllPlaylist()
    }

    fun getAllPlaylist(){
        viewModelScope.launch {
            userRepository.getAllPlaylist()
        }
    }
}