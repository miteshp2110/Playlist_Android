package com.xceptions.playlist.model.song

data class SearchSongsItem(
    val artistName: String,
    val duration: Int,
    var isFavourite: Int,
    val songId: Int,
    val songName: String,
    val song_image_url: String
)