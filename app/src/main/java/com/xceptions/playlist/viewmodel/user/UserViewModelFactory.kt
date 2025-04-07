package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserViewModelFactory(private val token : String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserHomeViewModel::class.java)){
            return UserHomeViewModel(token) as T
        }
        if(modelClass.isAssignableFrom(UserSearchViewModel::class.java)){
            return UserSearchViewModel(token) as T
        }
        if(modelClass.isAssignableFrom(UserPlaylistViewModel::class.java)){
            return UserPlaylistViewModel(token) as T
        }
        if(modelClass.isAssignableFrom(UserAccountViewModel::class.java)){
            return UserAccountViewModel(token) as T
        }
        if(modelClass.isAssignableFrom(PlaylistSharedViewModel::class.java)){
            return PlaylistSharedViewModel(token) as T
        }
        if(modelClass.isAssignableFrom(FavouritesViewModel::class.java)){
            return FavouritesViewModel(token) as T
        }
        if(modelClass.isAssignableFrom(ArtistViewModel::class.java)){
            return ArtistViewModel(token) as T
        }
        throw IllegalArgumentException("Unknown Agrument")
    }
}