package com.xceptions.playlist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.repository.AdminRepository
import kotlinx.coroutines.launch

class LanguageViewModel(token:String):ViewModel() {

    private val adminRepository = AdminRepository(token)

    val allLanguagesData : LiveData<GetLanguages?> = adminRepository.allLanguages

    init {
        getAllLanguages()
    }
    private fun getAllLanguages(){
        viewModelScope.launch {
            adminRepository.getAllLanguages()
        }
    }
}