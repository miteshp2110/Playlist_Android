package com.xceptions.playlist.utils

import RoundedTransformation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites

class FavouriteAdapter (private val faourite: GetAllFavourites): RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val favSongName : TextView = itemView.findViewById(R.id.favouriteSongName)
        val favArtistName : TextView = itemView.findViewById(R.id.favouriteArtistName)
        val favSongImage : ImageView = itemView.findViewById(R.id.favouriteSongImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteAdapter.FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_song_card,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteAdapter.FavouriteViewHolder, position: Int) {
        val fav = faourite[position]
        holder.favSongName.text = fav.songName
        holder.favArtistName.text = fav.artistName
        val imageUrl = fav.song_image_url
        Picasso.get()
            .load(imageUrl)
            .into(holder.favSongImage)
    }

    override fun getItemCount(): Int {
        return faourite.size
    }

}