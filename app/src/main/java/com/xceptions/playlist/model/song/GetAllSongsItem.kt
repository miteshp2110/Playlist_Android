package com.xceptions.playlist.model.song

data class GetAllSongsItem(
    val artist: Int,
    val duration: Int,
    val genere: Int,
    val id: Int,
    val language: Int,
    val name: String,
    val song_image_url: String,
    val song_url: String
)