package com.xceptions.playlist.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xceptions.playlist.R
import com.xceptions.playlist.model.Languages.GetLanguages

class LanguageAdapter (private val languages:GetLanguages): RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>(){

    inner class LanguageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val languageId : TextView = itemView.findViewById(R.id.languageId)
        val languageName : TextView = itemView.findViewById(R.id.languageName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageAdapter.LanguageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.language_card,parent,false)
        return LanguageViewHolder(view)
    }

    override fun onBindViewHolder(holder: LanguageAdapter.LanguageViewHolder, position: Int) {
        val language = languages[position]
        holder.languageId.text = language.id.toString()
        holder.languageName.text = language.name
    }

    override fun getItemCount(): Int {
        return languages.size
    }


}