package com.xceptions.playlist.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.playlist.GetAllPlaylist

class PlaylistAdapter(private val playlists : GetAllPlaylist) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    inner class PlaylistViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val playlistName : TextView = itemView.findViewById(R.id.playlistName)
        val playlistTotalSongs : TextView = itemView.findViewById(R.id.playlistTotalSongs)
        val playlistImage : ImageView = itemView.findViewById(R.id.playlistImage)
        val playlistDuration : TextView = itemView.findViewById(R.id.playlistTotalDuration)
        val playlistPlayButton : ImageView = itemView.findViewById(R.id.playlistPlayButton)
        val playlistCardId : CardView = itemView.findViewById(R.id.playListId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_card,parent,false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter.PlaylistViewHolder, position: Int) {
        val playlistItem = playlists[position]
        holder.playlistName.text = playlistItem.playlist_name
        holder.playlistTotalSongs.text = playlistItem.total_songs.toString()
        val imgUrl = playlistItem.playlist_image_url
        Picasso.get()
            .load(imgUrl)
            .into(holder.playlistImage)
        val dur: Int = playlistItem.total_duration_seconds.toInt()
        val min : Int = dur/60
        val secInt : Int = dur - min*60
        val secStr : String = min.toString()+":"+if(secInt<10){"0"+secInt.toString()}else{secInt.toString()}
        Log.d("playlist",secStr)
        holder.playlistDuration.text = secStr

        holder.playlistPlayButton.setOnClickListener{
            Log.d("playlist","play the playlist with id : "+playlistItem.playlist_id)
        }
        holder.playlistCardId.setOnClickListener{
            Log.d("playlist","open the playlist with id : "+playlistItem.playlist_id)
        }

    }

    override fun getItemCount(): Int {
        return playlists.size
    }
}