package com.xceptions.playlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AdminViewModelFactory(private val token:String): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LanguageViewModel::class.java)){
            return LanguageViewModel(token) as T
        }
        if (modelClass.isAssignableFrom(GenreViewModel::class.java)){
            return GenreViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown Agrument")
    }
}