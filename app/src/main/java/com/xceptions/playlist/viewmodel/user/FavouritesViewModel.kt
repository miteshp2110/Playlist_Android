package com.xceptions.playlist.viewmodel.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.model.favourites.GetAllFavouritesItem
import com.xceptions.playlist.model.favourites.RemoveFavourite
import com.xceptions.playlist.repository.UserRepository
import kotlinx.coroutines.launch

class FavouritesViewModel (token : String) : ViewModel() {
    private var apiService = UserRepository(token)

    var allFavData = apiService._allFavourite

    fun removeSongFromFav(favItem : GetAllFavouritesItem){
        viewModelScope.launch {
            apiService.removeFromFav(RemoveFavourite(favItem.favId))
            var newFavs = allFavData.value
            newFavs!!.remove(favItem)
            allFavData.value = newFavs
        }
    }
    init {
        viewModelScope.launch {
            apiService.getAllFavourite()
        }
    }



}