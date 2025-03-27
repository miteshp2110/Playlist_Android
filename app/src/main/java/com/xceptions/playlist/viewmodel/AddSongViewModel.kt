package com.xceptions.playlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.repository.AdminRepository
import kotlinx.coroutines.launch

class AddSongViewModel (token : String) : ViewModel() {

    private val adminRepository = AdminRepository(token)

    val allSongsResponse : LiveData<GetAllSongs?> = adminRepository.allSongs
    private var page : Int = 1


    init {
        Log.d("addsongs","init with page $page")
        loadSongs()
    }

    fun loadSongs(){
        getAllSongs(page++)
    }

    private fun getAllSongs(page:Int){
        viewModelScope.launch {
            adminRepository.getAllSongs(page)
        }
    }

}