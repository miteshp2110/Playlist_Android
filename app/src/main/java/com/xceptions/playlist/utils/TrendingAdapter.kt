package com.xceptions.playlist.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.song.GetTrendingSongs

class TrendingAdapter (private val trending : GetTrendingSongs):RecyclerView.Adapter<TrendingAdapter.TrendingViewHolder>() {
    inner class TrendingViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val songName : TextView = itemView.findViewById(R.id.trendingSongName)
        val songImage : ImageView = itemView.findViewById(R.id.songImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trending_song_card,parent,false)
        return TrendingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trending.size
    }

    override fun onBindViewHolder(holder: TrendingViewHolder, position: Int) {
        val trendingSong = trending[position]
        holder.songName.text = trendingSong.name
        val image_url = trendingSong.song_image_url
        Picasso.get()
            .load(image_url)
            .into(holder.songImage)
    }
}