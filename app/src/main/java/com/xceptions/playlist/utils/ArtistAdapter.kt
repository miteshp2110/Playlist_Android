package com.xceptions.playlist.utils

import RoundedTransformation
import android.content.Context
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
import com.xceptions.playlist.model.song.GetSongsByArtistItem

class ArtistAdapter (private val artists: GetAllArtist): RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    inner class ArtistViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val artistName : TextView = itemView.findViewById(R.id.artistName)
        val artistImage : ImageView = itemView.findViewById(R.id.artistImage)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistAdapter.ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_card,parent,false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistAdapter.ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.artistName.text = artist.name
        val imageUrl = artist.profile_image
        Picasso.get()
            .load(imageUrl)
            .transform(RoundedTransformation(40f))
            .placeholder(R.drawable.placeholder)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.artistImage)

    }

    override fun getItemCount(): Int {
        return artists.size
    }

}