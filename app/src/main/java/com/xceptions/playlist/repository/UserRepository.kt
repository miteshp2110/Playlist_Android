package com.xceptions.playlist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.network.user.RetrofitClient
import com.xceptions.playlist.network.user.UserApiService
import retrofit2.Response

class UserRepository(private val token : String) {
    private val apiService : UserApiService = RetrofitClient.getClient(token).create(UserApiService::class.java)

    private var _trendingSongs = MutableLiveData<GetTrendingSongs?>()
    val trendingSongs : LiveData<GetTrendingSongs?> = _trendingSongs

    private var _topArtist = MutableLiveData<GetAllArtist?>()
    val topArtist : LiveData<GetAllArtist?> = _topArtist

    private var _homeFavourite = MutableLiveData<GetAllFavourites?>()
    val homeFavourites : LiveData<GetAllFavourites?> = _homeFavourite

    private var _searchSongResult = MutableLiveData<SearchSongs?> ()
    val searchSongResult : LiveData<SearchSongs?> = _searchSongResult

    suspend fun searchSongs(name : String){
        try{
            val response : Response<SearchSongs> = apiService.searchSong(name)
            if(response.isSuccessful){

                _searchSongResult.value = response.body()
            }
            else{

                _searchSongResult.value = null
            }
        }
        catch (e:Exception){

            _searchSongResult.value = null
        }
    }

    suspend fun getTrendingSongs(){
        try{
            val response : Response<GetTrendingSongs> = apiService.getTendingSongs()
            if(response.isSuccessful){

                _trendingSongs.value = response.body()
            }
            else{

                _trendingSongs.value = null
            }
        }
        catch (e:Exception){

            _trendingSongs.value = null
        }
    }

    suspend fun getHomeFavourite(){
        try{
            val response : Response<GetAllFavourites> = apiService.getHomeFavourite()
            if(response.isSuccessful){

                _homeFavourite.value = response.body()
            }
            else{
                _homeFavourite.value = null
            }
        }
        catch (e:Exception){

            _homeFavourite.value = null
        }
    }

    suspend fun getTopArtist(){
        try{
            val response : Response<GetAllArtist> = apiService.getTopArtists()
            if(response.isSuccessful){

                _topArtist.value = response.body()
            }
            else{
                _topArtist.value = null
            }
        }
        catch (e:Exception){

            _topArtist.value = null
        }
    }


}