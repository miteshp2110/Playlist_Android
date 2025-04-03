package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.model.song.GetAllSongsItem
import com.xceptions.playlist.repository.UserRepository

class PlaylistSharedViewModel(private val token : String) : ViewModel() {

    private val userRepository : UserRepository = UserRepository(token)

    var createPlayListName : String = ""
    var totalDuration = MutableLiveData<Int> (0)
    var totalSongs = MutableLiveData<Int> (0)

    var songsList = MutableLiveData<ArrayList<GetAllSongsItem>> (ArrayList<GetAllSongsItem>())



}