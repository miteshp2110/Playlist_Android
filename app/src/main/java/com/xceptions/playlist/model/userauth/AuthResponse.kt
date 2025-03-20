package com.xceptions.playlist.model.userauth

data class AuthResponse(
    val username : String,
    val email : String,
    val profileUrl : String,
    val jwtToken : String
)
