package com.xceptions.playlist.model.userauth

data class AuthResponse(
    val Message: String,
    val token: String,
    val email: String,
    val name : String,
    val profile_url: String
)