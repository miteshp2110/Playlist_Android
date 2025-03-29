package com.xceptions.playlist.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.repository.AdminRepository
import kotlinx.coroutines.launch

class AddArtistViewModel (token:String): ViewModel() {

    private val adminRepository = AdminRepository(token)

    var artistName: String? = null
    var imageUri : Uri? = null


    private val _allArtists = adminRepository.allArtists
    val allArtists : LiveData<GetAllArtist?> = _allArtists

    init {
        getAllArtists()
    }

    fun getAllArtists(){
        viewModelScope.launch {
            adminRepository.getAllArtists()
        }
    }
}