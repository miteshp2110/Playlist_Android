package com.xceptions.playlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xceptions.playlist.model.adminauth.AdminAuthRequest
import com.xceptions.playlist.model.adminauth.AdminAuthResponse
import com.xceptions.playlist.model.userauth.AuthRequest
import com.xceptions.playlist.model.userauth.AuthResponse
import com.xceptions.playlist.repository.AuthRepository

class AdminLoginViewModel : ViewModel(){
    private val authRepository = AuthRepository()

    private val _loginAdmin = MutableLiveData<AdminAuthResponse?>()

    val loginAdminResponse : LiveData<AdminAuthResponse?> = _loginAdmin

    fun loginAdmin(request: AdminAuthRequest){
        authRepository.loginAdmin(request).observeForever{response -> _loginAdmin.value=response}
    }

}