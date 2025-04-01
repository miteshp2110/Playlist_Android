package com.xceptions.playlist.viewmodel.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminViewModelFactory(private val token:String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AddMetaViewModel::class.java)){
            return AddMetaViewModel(token) as T
        }

        if(modelClass.isAssignableFrom(AddSongViewModel::class.java)){
            return AddSongViewModel(token) as T
        }

        if(modelClass.isAssignableFrom(AddArtistViewModel::class.java)){
            return AddArtistViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown Agrument")
    }
}