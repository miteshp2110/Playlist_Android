package com.xceptions.playlist.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.R
import com.xceptions.playlist.model.song.MiniPlayerSong
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Stack
class UserActivityViewModel(token:String):ViewModel() {


    var activeItemId : Int? = null
    private val userRepository = UserRepository(token)

    private var _currentSong : LiveData<MiniPlayerSong?> = userRepository.getSongById
    val currentSongId = _currentSong
    private val previousSongsStack = Stack<Int>()
    private val songsLL =LinkedList<Int>()
    private val songsSet = mutableSetOf<Int>()


    fun playSong(songId : Int,autoPlay:Boolean = false,isPrevious:Boolean = false){
        viewModelScope.launch {
            Log.d("miniPlayer","Fetch Details for Song With Id: $songId")
            userRepository.getSongsById(songId)
            Log.d("miniplayer","Fetched Details")
            if(!autoPlay){
                songsLL.clear()
                songsLL.add(songId)
            }
            songsSet.add(songId)
            if(!isPrevious){
                if(songsLL.size<3){
                    var nextSong = fetchNextSong(songId)
                    while(songsSet.contains(nextSong)){
                        nextSong = fetchNextSong(songId)
                    }
                    songsLL.add(nextSong)
                }
            }
        }
    }
    fun play(){
        Log.d("miniPlayer","play the paused song")
    }
    fun pause(){
        Log.d("miniPlayer","pause the playing song")
    }

    fun nextSong(){

    }

    init {
        activeItemId = R.id.nav_home
    }

    fun setActiveFragment( navItemId:Int){
        activeItemId = navItemId
    }

    fun fetchNextSong(currentSongId : Int):Int{
        var newSongId : Int = -1
        while (songsSet.contains(newSongId)){

            newSongId = -1 // get new id api call

            if(newSongId == 0){
                //no songs left in db reset the player
                val nextSng:Int= previousSongsStack[0]
                previousSongsStack.clear()
                songsSet.clear()
                return nextSng
            }
        }
        return newSongId
    }

    fun addSongToLL(songId:Int){
        songsLL.add(songId)
        songsSet.add(songId)
    }


}