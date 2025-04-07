package com.xceptions.playlist.utils

import RoundedTransformation
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.model.song.GetSongsByArtist

class ArtistSongAdapter(private val songs : GetSongsByArtist) : RecyclerView.Adapter<ArtistSongAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val songName : TextView = itemView.findViewById(R.id.artistSongName)
        val songCoverImage : ImageView = itemView.findViewById(R.id.artistSongImage)
//        val artistSongCard : LinearLayout = itemView.findViewById(R.id.artistCardId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistSongAdapter.SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_artist_song,parent,false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistSongAdapter.SongViewHolder, position: Int) {
        val song = songs[position]
        holder.songName.text = song.name
        val imgUrl = song.song_image_url
        Picasso.get()
            .load(imgUrl)
            .into(holder.songCoverImage)

    }

    override fun getItemCount(): Int {
        return songs.size
    }
}