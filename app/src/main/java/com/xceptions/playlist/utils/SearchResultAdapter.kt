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

class SearchResultAdapter(private val searches : SearchSongs) : RecyclerView.Adapter<SearchResultAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val songName : TextView = itemView.findViewById(R.id.searchSongName)
        val artistName : TextView = itemView.findViewById(R.id.searchArtistName)
        val songCoverImage : ImageView = itemView.findViewById(R.id.searchSongImage)
        val favIcon : ImageView = itemView.findViewById(R.id.searchFavIcon)
        val songDuration : TextView = itemView.findViewById(R.id.searchSongDuration)
        val favId : TextView = itemView.findViewById(R.id.favId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultAdapter.SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_card,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultAdapter.SearchViewHolder, position: Int) {
        val search = searches[position]
        holder.songName.text = search.songName
        holder.artistName.text = search.artistName
        holder.favId.text= search.isFavourite.toString()

        if(search.isFavourite == 0){
            holder.favIcon.setImageResource(R.drawable.icon_favourite_inactive)
        }
        else{
            holder.favIcon.setImageResource(R.drawable.icon_favourite_active)
        }
        holder.favIcon.setOnClickListener{
            Log.d("search","song id : "+search.songId+" favId : "+search.isFavourite)
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