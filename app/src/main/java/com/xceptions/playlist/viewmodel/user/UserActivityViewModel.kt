package com.xceptions.playlist.viewmodel.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.R
import com.xceptions.playlist.model.song.MiniPlayerSong
import com.xceptions.playlist.model.song.NextSong
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

    var isPlaying : Boolean = false


    fun playSong(songId : Int,autoPlay:Boolean = false,isPrevious:Boolean = false){
        viewModelScope.launch {

            if(isPrevious){
                userRepository.getSongsById(songId)
            }
            else{
                userRepository.getSongsById(songId)
                if(!autoPlay && !isPrevious){
                    songsLL.clear()
                    songsSet.clear()
                    songsLL.add(songId)
                }
                if(songsSet.contains(songId)){
//                    Log.d("miniplayer","dupicated song in set $songId")
                    songsSet.remove(songId)
                    songsSet.add(songId)
                }
                else{
                    songsSet.add(songId)
                }
                if(!isPrevious){
                    if(songsLL.size<2){
//                        Log.d("miniplayer","Initiatd getting next song")
                        var nextSong = userRepository.getNextSong(NextSong(songId,songsSet.toList()))
                        if (nextSong == 0){

//                            Log.d("miniplayer","got next song as 0")
                            songsSet.clear()
                            songsLL.add(previousSongsStack[0])
//                            Log.d("miniplayer","new ll as $songsLL")
                        }
                        else{
                            songsLL.add(nextSong)
                        }
//                        Log.d("miniplayer","Added next song to ll : $songsLL")
                    }
                }

            }

        }
    }



    fun nextSong(){
        previousSongsStack.add(songsLL.removeFirst())
        songsLL.peek()?.let { playSong(it,true) }
    }

    fun prevSong(){
        if(!previousSongsStack.isEmpty()){
            songsLL.addFirst(previousSongsStack.pop())
            songsLL.peek()?.let { playSong(it,true,isPrevious=true) }
        }
        else{
            Log.d("miniplayer","No prev avl")
        }
    }

    init {
        activeItemId = R.id.nav_home
    }

    fun setActiveFragment( navItemId:Int){
        activeItemId = navItemId
    }

    fun playPlaylist(id:Int){
        viewModelScope.launch {
            previousSongsStack.clear()
            songsSet.clear()
            songsLL.clear()
            val playListSongs = userRepository.getPlaylistSongs(id)!!
            songsLL.addAll(playListSongs)
            playSong(songsLL.peek()!!, isPrevious = true)
        }
    }



}