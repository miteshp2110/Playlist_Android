package com.xceptions.playlist.viewmodel.admin

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.MessageResponse
import com.xceptions.playlist.model.NameRequestBody
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.repository.AdminRepository
import kotlinx.coroutines.launch

class AddMetaViewModel (token:String): ViewModel() {

    private val adminRepository = AdminRepository(token)

    val allGenreData : LiveData<GetGenre?> = adminRepository.allGenre
    val allLanguagesData : LiveData<GetLanguages?> = adminRepository.allLanguages
    private val _addLanguage = MutableLiveData<MessageResponse?>()
    val addLanguageResponse : LiveData<MessageResponse?> = _addLanguage
    private val _addGenre = MutableLiveData<MessageResponse?>()
    val addGenreResponse : LiveData<MessageResponse?> = _addGenre

    init {
        getAllLanguages()
        getAllGenre()
    }

    fun getAllLanguages(){
        viewModelScope.launch {
            adminRepository.getAllLanguages()
        }
    }
    fun getAllGenre(){
        viewModelScope.launch {
            adminRepository.getAllGenre()
        }
    }

    fun addLanguage(requestBody: NameRequestBody,context: LifecycleOwner){
        adminRepository.addLanguage(requestBody).observe(context){ response ->
            _addLanguage.value = response
        }
    }

    fun addGenre(requestBody: NameRequestBody,context: LifecycleOwner){
        adminRepository.addGenre(requestBody).observe(context){response ->
            _addGenre.value = response
        }
    }



}