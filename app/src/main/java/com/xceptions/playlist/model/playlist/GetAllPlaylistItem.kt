package com.xceptions.playlist.model.playlist

data class GetAllPlaylistItem(
    val playlist_id: Int,
    val playlist_image_url: String,
    val playlist_name: String,
    val total_duration_seconds: String,
    val total_songs: Int
)