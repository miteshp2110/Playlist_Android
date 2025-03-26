package com.xceptions.playlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.repository.AdminRepository
import kotlinx.coroutines.launch

class GenreViewModel (token:String):ViewModel(){

    private val adminRepository = AdminRepository(token)

    val allGenreData : LiveData<GetGenre?> = adminRepository.allGenre

    init {
        getAllGenre()
    }

    private fun getAllGenre(){
        viewModelScope.launch {
            adminRepository.getAllGenre()
        }
    }

}