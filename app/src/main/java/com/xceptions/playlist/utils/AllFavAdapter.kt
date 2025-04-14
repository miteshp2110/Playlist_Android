package com.xceptions.playlist.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.viewmodel.user.FavouritesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllFavAdapter(private val favs : GetAllFavourites,private val viewModel : FavouritesViewModel,private val onSongClickListener: OnSongClickListener) : RecyclerView.Adapter<AllFavAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val songName : TextView = itemView.findViewById(R.id.allFavSongName)
        val artistName : TextView = itemView.findViewById(R.id.allFavArtistName)
        val songCoverImage : ImageView = itemView.findViewById(R.id.allFavSongImage)
        val favIcon : ImageView = itemView.findViewById(R.id.allFavDelIcon)
        val favCard : CardView = itemView.findViewById(R.id.allFavSongId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFavAdapter.SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_fav_card,parent,false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllFavAdapter.SearchViewHolder, position: Int) {
        val fav = favs[position]
        holder.songName.text = fav.songName
        holder.artistName.text = fav.artistName


        holder.favIcon.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.removeSongFromFav(fav)
            }
        }
        holder.favCard.setOnClickListener {
            onSongClickListener.onClick(fav.songId)
        }

        val imgUrl = fav.song_image_url
        Picasso.get()
            .load(imgUrl)
            .into(holder.songCoverImage)

    }

    override fun getItemCount(): Int {
        return favs.size
    }
}