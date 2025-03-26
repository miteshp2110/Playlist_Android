package com.xceptions.playlist.repository

import RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.network.admin.AdminApiInterface
import retrofit2.Callback
import retrofit2.Response


class AdminRepository(token:String) {

    private val apiService : AdminApiInterface = RetrofitClient.getClient(token).create(AdminApiInterface::class.java)

    private val _allLanguages = MutableLiveData<GetLanguages?>()
    val allLanguages :LiveData<GetLanguages?> = _allLanguages

    private val _allGenre = MutableLiveData<GetGenre?>()
    val allGenre :LiveData<GetGenre?> = _allGenre

    suspend fun getAllLanguages(){
        try {

            val response : Response<GetLanguages> = apiService.getAllLanguages()
            if(response.isSuccessful){
                _allLanguages.postValue(response.body())
            }
            else{
                _allLanguages.postValue(null)
            }
        }
        catch (e:Exception){
            _allLanguages.postValue(null)
        }
    }

    suspend fun getAllGenre(){
        try{
            val response : Response<GetGenre> = apiService.getAllGenre()
            if(response.isSuccessful){
                _allGenre.postValue(response.body())
            }
            else{
                Log.d("genre","error")
                _allGenre.postValue(null)
            }

        }
        catch(e:Exception){
            _allGenre.postValue(null)
        }
    }

}
