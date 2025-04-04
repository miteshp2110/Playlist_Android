package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.model.song.GetAllSongsItem
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class PlaylistSharedViewModel(private val token : String) : ViewModel() {

    private val userRepository : UserRepository = UserRepository(token)

    var createPlayListName : String = ""
    var totalDuration = MutableLiveData<Int> (0)
    var totalSongs = MutableLiveData<Int> (0)

    var songsList = MutableLiveData<ArrayList<GetAllSongsItem>> (ArrayList<GetAllSongsItem>())

    val searchResponse : LiveData<SearchSongs?> = userRepository.searchSongResult

    fun searchSong(name : String){
        viewModelScope.launch {
            userRepository.searchSongs(name)
        }
    }


}