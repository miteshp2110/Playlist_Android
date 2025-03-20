package com.xceptions.playlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xceptions.playlist.model.userauth.AuthRequest
import com.xceptions.playlist.model.userauth.AuthResponse
import com.xceptions.playlist.repository.AuthRepository

class AuthViewModel : ViewModel(){
    private val authRepository = AuthRepository()

    private val _authenticateResponse = MutableLiveData<AuthResponse?>()

    val authenticateResponse : LiveData<AuthResponse?> = _authenticateResponse

    fun authenticateUser(request: AuthRequest){
        authRepository.authenticateUser(request).observeForever{response -> _authenticateResponse.value=response}
    }

}