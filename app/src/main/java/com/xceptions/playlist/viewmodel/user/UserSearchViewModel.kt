package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.favourites.AddFavourite
import com.xceptions.playlist.model.favourites.RemoveFavourite
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class UserSearchViewModel(private val token:String) : ViewModel() {

    private val userRepository : UserRepository = UserRepository(token)

    val searchResponse : LiveData<SearchSongs?> = userRepository.searchSongResult

    fun searchSong(name : String){
        viewModelScope.launch {
            userRepository.searchSongs(name)
        }
    }

    suspend fun addToFav(songId : Int) : Int{
        return suspendCancellableCoroutine { continuation ->
            userRepository.addSongTOFav(AddFavourite(songId)).observeForever { response ->
                val favId : Int = response?.Message?.toInt()?:-1
                continuation.resume(favId)
            }
        }
    }

    suspend fun RemoveFromFav(favId : Int){
        userRepository.removeFromFav(RemoveFavourite(favId))
    }

}