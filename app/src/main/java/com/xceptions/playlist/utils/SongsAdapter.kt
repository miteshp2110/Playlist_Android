package com.xceptions.playlist.utils

import RoundedTransformation
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.song.GetAllSongs

class SongsAdapter(private val songs : GetAllSongs) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    inner class SongViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val songName : TextView = itemView.findViewById(R.id.songName)
        val artistName : TextView = itemView.findViewById(R.id.artistName)
        val songCoverImage : ImageView = itemView.findViewById(R.id.songCoverImage)
        val songGenre : TextView = itemView.findViewById(R.id.songGenre)
        val songDuration : TextView = itemView.findViewById(R.id.songDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsAdapter.SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_card,parent,false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsAdapter.SongViewHolder, position: Int) {
        val song = songs[position]
        holder.songName.text = song.name
        holder.artistName.text = song.artist
        holder.songGenre.text = song.genere
        val imgUrl = song.song_image_url
        Log.d("addsongs",imgUrl)
        Picasso.get()
            .load(imgUrl)
            .transform(RoundedTransformation(50f))
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.songCoverImage)
        val dur: Int = song.duration
        val min : Int = dur/60
        val secInt : Int = dur - min*60
        val secStr : String = min.toString()+":"+if(secInt<10){"0"+secInt.toString()}else{secInt.toString()}
        holder.songDuration.text = secStr

    }

    override fun getItemCount(): Int {
        return songs.size
    }
}