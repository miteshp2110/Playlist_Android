package com.xceptions.playlist.model.playlist

data class CreatePlaylist(
    val playlistName: String,
    val songsList: List<Int>
)