package com.xceptions.playlist.utils

import RoundedTransformation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites

class FavouriteAdapter (private val faourite: GetAllFavourites, private val onSongClickListener: OnSongClickListener): RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val favSongName : TextView = itemView.findViewById(R.id.favouriteSongName)
        val favArtistName : TextView = itemView.findViewById(R.id.favouriteArtistName)
        val favSongImage : ImageView = itemView.findViewById(R.id.favouriteSongImage)
        val favCard : CardView = itemView.findViewById(R.id.favSongCard)
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
        holder.favCard.setOnClickListener {
            onSongClickListener.onClick(fav.songId)
        }
    }

    override fun getItemCount(): Int {
        return faourite.size
    }

}