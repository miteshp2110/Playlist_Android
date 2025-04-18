package com.xceptions.playlist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.MessageResponse
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.model.favourites.AddFavourite
import com.xceptions.playlist.model.favourites.RemoveFavourite
import com.xceptions.playlist.model.playlist.GetAllPlaylist
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.network.user.RetrofitClient
import com.xceptions.playlist.network.user.UserApiService
import retrofit2.Call
import retrofit2.Callback
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

    private val _getAllPlaylistResult = MutableLiveData<GetAllPlaylist?>()
    val getAllPlaylistResult : LiveData<GetAllPlaylist?> = _getAllPlaylistResult

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

    fun addSongTOFav(body : AddFavourite): LiveData<MessageResponse?>{
        val responseData = MutableLiveData<MessageResponse?>()

        apiService.addFavourite(body).enqueue(object : Callback<MessageResponse?>{
            override fun onResponse(call: Call<MessageResponse?>, response: Response<MessageResponse?>) {
                if(response.isSuccessful){
                    responseData.value = response.body()
                }
                else{
                    responseData.value = null
                }
            }

            override fun onFailure(call: Call<MessageResponse?>, t: Throwable) {
                responseData.value = null
            }

        })
        return responseData
    }

    fun removeFromFav(body : RemoveFavourite){
        apiService.removeSongFromFav(body).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(p0: Call<MessageResponse>, p1: Response<MessageResponse>) {

            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {

            }

        })
    }

    suspend fun getAllPlaylist(){
        try{
            val response : Response<GetAllPlaylist> = apiService.getAllPlaylist()

            if(response.isSuccessful){

                _getAllPlaylistResult.value = response.body()
            }
            else{

                _getAllPlaylistResult.value = null
            }
        }
        catch (e:Exception){

            _getAllPlaylistResult.value = null
        }
    }

}