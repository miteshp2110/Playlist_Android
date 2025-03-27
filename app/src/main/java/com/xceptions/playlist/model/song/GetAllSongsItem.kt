package com.xceptions.playlist.model.song

data class GetAllSongsItem(
    val artist: String,
    val duration: Int,
    val genere: String,
    val id: Int,
    val language: String,
    val name: String,
    val song_image_url: String
)