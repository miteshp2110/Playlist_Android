package com.xceptions.playlist.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xceptions.playlist.R
import com.xceptions.playlist.model.genre.GetGenre

class GenreAdapter(private val genre: GetGenre): RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    inner class GenreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val languageId : TextView = itemView.findViewById(R.id.languageId)
        val languageName : TextView = itemView.findViewById(R.id.languageName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.language_card,parent,false)
        return GenreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return genre.size
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genre[position]
        holder.languageId.text = genre.id.toString()
        holder.languageName.text = genre.name
    }
}