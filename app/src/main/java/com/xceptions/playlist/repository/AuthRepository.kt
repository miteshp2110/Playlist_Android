package com.xceptions.playlist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.userauth.AuthRequest
import com.xceptions.playlist.model.userauth.AuthResponse
import com.xceptions.playlist.network.RetrofitClient
import com.xceptions.playlist.network.auth.AuthApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    private val authApiService : AuthApiInterface = RetrofitClient.instance.create(AuthApiInterface::class.java)

    fun authenticateUser(request: AuthRequest):LiveData<AuthResponse?>{

        val responseLiveData = MutableLiveData<AuthResponse?>()

        authApiService.authenticateUser(request).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                if(response.isSuccessful){

                    responseLiveData.value = response.body()
                }
                else{
                    if(response.code()==307){
                        responseLiveData.value = AuthResponse("isAdmin","","","","")
                        return
                    }
                    responseLiveData.value = null
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("tester","failed")
                responseLiveData.value= null
            }

        })

        return responseLiveData
    }
}