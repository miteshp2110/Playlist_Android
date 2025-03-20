package com.xceptions.playlist.network.auth

import com.xceptions.playlist.model.userauth.AuthRequest
import com.xceptions.playlist.model.userauth.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiInterface {
    @POST("auth/user/authenticate")
    fun authenticateUser(@Body request: AuthRequest) : Call<AuthResponse>
}