package com.xceptions.playlist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.network.user.RetrofitClient
import com.xceptions.playlist.network.user.UserApiService
import retrofit2.Response

class UserRepository(private val token : String) {
    private val apiService : UserApiService = RetrofitClient.getClient(token).create(UserApiService::class.java)

    private var _trendingSongs = MutableLiveData<GetTrendingSongs?>()
    val trendingSongs : LiveData<GetTrendingSongs?> = _trendingSongs


    suspend fun getTrendingSongs(){
        try{
            val response : Response<GetTrendingSongs> = apiService.getTendingSongs()
            if(response.isSuccessful){
                Log.d("home",response.body().toString())
                _trendingSongs.value = response.body()
            }
            else{
                Log.d("home",response.body().toString())
                _trendingSongs.value = null
            }
        }
        catch (e:Exception){
            Log.d("error",e.message.toString())
            _trendingSongs.value = null
        }
    }


}