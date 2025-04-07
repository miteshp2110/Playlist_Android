package com.xceptions.playlist.utils

import com.xceptions.playlist.model.song.GetSongsByArtistItem

interface OnArtistClickListener {
    fun onArtistClick(artistId:Int,artistName:String,artistImage:String)
}