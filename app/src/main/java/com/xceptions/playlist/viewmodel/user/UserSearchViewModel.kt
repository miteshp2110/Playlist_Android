package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class UserSearchViewModel(private val token:String) : ViewModel() {

    private val userRepository : UserRepository = UserRepository(token)

    val searchResponse : LiveData<SearchSongs?> = userRepository.searchSongResult

    fun searchSong(name : String){
        viewModelScope.launch {
            userRepository.searchSongs(name)
        }
    }

}