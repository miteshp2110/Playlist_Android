package com.xceptions.playlist.network.auth

import com.xceptions.playlist.model.adminauth.AdminAuthRequest
import com.xceptions.playlist.model.adminauth.AdminAuthResponse
import com.xceptions.playlist.model.userauth.AuthRequest
import com.xceptions.playlist.model.userauth.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiInterface {
    @POST("auth/user/authenticate")
    fun authenticateUser(@Body request: AuthRequest) : Call<AuthResponse>

    @POST("auth/admin/login")
    fun loginAdmin(@Body request : AdminAuthRequest) : Call<AdminAuthResponse>
}