package com.xceptions.playlist.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.artist.GetAllArtist

class TopArtistAdapter (private val artists:GetAllArtist) : RecyclerView.Adapter<TopArtistAdapter.ArtistViewHolder> (){

    inner class ArtistViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val artistName : TextView = itemView.findViewById(R.id.topArtistName)
        val artistImage : ImageView = itemView.findViewById(R.id.topArtistImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_artist_card,parent,false)
        return ArtistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artists[position]
        holder.artistName.text = artist.name
        val image_url = artist.profile_image
        Picasso.get()
            .load(image_url)
            .into(holder.artistImage)
    }
}