package com.xceptions.playlist.repository

import RetrofitClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.network.admin.AdminApiInterface
import retrofit2.Callback
import retrofit2.Response


class AdminRepository(token:String) {

    private val apiService : AdminApiInterface = RetrofitClient.getClient(token).create(AdminApiInterface::class.java)

    private val _allLanguages = MutableLiveData<GetLanguages?>()
    val allLanguages :LiveData<GetLanguages?> = _allLanguages

    suspend fun getAllLanguages(){
        try {

            val response : Response<GetLanguages> = apiService.getAllLanguages()
            if(response.isSuccessful){
//                Log.d("languages",response.body().toString())
                _allLanguages.postValue(response.body())
            }
            else{
//                Log.d("languages",response.errorBody().toString())
                _allLanguages.postValue(null)
            }
        }
        catch (e:Exception){
            _allLanguages.postValue(null)
        }
    }

}
