package com.xceptions.playlist.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.viewmodel.user.PlaylistSharedViewModel
import com.xceptions.playlist.viewmodel.user.UserSearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditSongAdapter(private val searches : SearchSongs, private val viewModel : PlaylistSharedViewModel) : RecyclerView.Adapter<EditSongAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val songName : TextView = itemView.findViewById(R.id.editSearchSongName)
        val artistName : TextView = itemView.findViewById(R.id.editSearchArtistName)
        val songCoverImage : ImageView = itemView.findViewById(R.id.editSongImage)
        val checkBox : ImageView = itemView.findViewById(R.id.editDeleteIcon)
        val songDuration : TextView = itemView.findViewById(R.id.editsearchSongDuration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditSongAdapter.SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_song_card,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: EditSongAdapter.SearchViewHolder, position: Int) {
        val search = searches[position]

        holder.songName.text = search.songName
        holder.artistName.text = search.artistName

        holder.checkBox.setOnClickListener {
            viewModel.removeElementFromSongList(search)
        }

        val imgUrl = search.song_image_url
        Picasso.get()
            .load(imgUrl)
            .into(holder.songCoverImage)
        val dur: Int = search.duration
        val min : Int = dur/60
        val secInt : Int = dur - min*60
        val secStr : String = min.toString()+":"+if(secInt<10){"0"+secInt.toString()}else{secInt.toString()}
        holder.songDuration.text = secStr

    }

    override fun getItemCount(): Int {
        return searches.size
    }
}