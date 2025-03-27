package com.xceptions.playlist.viewmodel

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
        throw IllegalArgumentException("Unknown Agrument")
    }
}