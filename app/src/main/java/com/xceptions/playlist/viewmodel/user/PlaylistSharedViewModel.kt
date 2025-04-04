package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.model.song.GetAllSongsItem
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.model.song.SearchSongsItem
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class PlaylistSharedViewModel(private val token : String) : ViewModel() {

    private val userRepository : UserRepository = UserRepository(token)

    var createPlayListName : String = ""
    var totalDuration = MutableLiveData<Int> (0)
    var totalSongs = MutableLiveData<Int> (0)


    var songsList = MutableLiveData<SearchSongs> (SearchSongs())

    fun addElementToSongList(song : SearchSongsItem){
        var currentSong = songsList.value
        currentSong?.add(song)
        songsList.value = currentSong!!
        totalSongs.value = songsList.value!!.size
        var currentDuration = totalDuration.value!!
        currentDuration += song.duration
        totalDuration.value = currentDuration
    }

    fun isElementThere(song : SearchSongsItem):Boolean{
        return songsList.value!!.contains(song)
    }

    fun removeElementFromSongList(song:SearchSongsItem){
        var currentDuration = totalDuration.value!!
        currentDuration -= song.duration
        totalDuration.value = currentDuration
        var currentSong = songsList.value
        currentSong?.remove(song)
        songsList.value = currentSong!!
        totalSongs.value = songsList.value!!.size
    }


    val searchResponse : LiveData<SearchSongs?> = userRepository.searchSongResult

    fun searchSong(name : String){
        viewModelScope.launch {
            userRepository.searchSongs(name)
        }
    }


}