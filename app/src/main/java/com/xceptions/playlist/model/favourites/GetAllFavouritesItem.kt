package com.xceptions.playlist.model.favourites

data class GetAllFavouritesItem(
    val favId: Int,
    val songName: String,
    val songId: Int,
    val song_image_url: String,
    val artistName : String
)