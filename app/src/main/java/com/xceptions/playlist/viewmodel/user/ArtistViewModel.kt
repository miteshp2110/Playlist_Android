package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.song.GetSongsByArtist
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class ArtistViewModel(token:String) : ViewModel() {
    private val apiService : UserRepository = UserRepository(token)

    val songsByArtist : LiveData<GetSongsByArtist?> = apiService.songsByArtist

    fun getAllSongsByArtist(id:Int){
        viewModelScope.launch {
            apiService.getSongsByArtist(id)
        }
    }
}