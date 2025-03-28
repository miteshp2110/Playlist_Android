package com.xceptions.playlist.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.Languages.GetLanguagesItem
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.repository.AdminRepository
import kotlinx.coroutines.launch

class AddSongViewModel (token : String) : ViewModel() {

    private val adminRepository = AdminRepository(token)

    var imageUri: Uri?= null
    var songUri: Uri? = null
    var songName: String? = null
    var languageId : String? =null
    var genereId : String? = null
    var artistId : String? = null

    val allSongsResponse : LiveData<GetAllSongs?> = adminRepository.allSongs
    private var page : Int = 1
    var languagesList = adminRepository.allLanguages

    init {
        loadSongs()
        viewModelScope.launch {
            adminRepository.getAllLanguages()
        }
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