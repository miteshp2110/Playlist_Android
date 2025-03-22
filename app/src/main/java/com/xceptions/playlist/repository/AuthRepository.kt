package com.xceptions.playlist.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.adminauth.AdminAuthRequest
import com.xceptions.playlist.model.adminauth.AdminAuthResponse
import com.xceptions.playlist.model.userauth.AuthInit
import com.xceptions.playlist.model.userauth.AuthRequest
import com.xceptions.playlist.model.userauth.AuthResponse
import com.xceptions.playlist.network.RetrofitClient
import com.xceptions.playlist.network.auth.AuthApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class AuthRepository {

    private val authApiService : AuthApiInterface = RetrofitClient.instance.create(AuthApiInterface::class.java)

    fun authenticateUser(request: AuthRequest):LiveData<AuthResponse?>{

        val responseLiveData = MutableLiveData<AuthResponse?>()

        authApiService.authenticateUser(request).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                if(response.isSuccessful){
                    if((response.body()?.Message ?: "null") == "isAdmin"){
                        responseLiveData.value = AuthResponse("isAdmin","",response.body()?.email?:"null","","")
                        return
                    }

                    responseLiveData.value = response.body()
                }
                else{

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

    fun loginAdmin(request : AdminAuthRequest) : LiveData<AdminAuthResponse?>{
        val responseLiveData = MutableLiveData<AdminAuthResponse?>()
        authApiService.loginAdmin(request).enqueue(object : Callback<AdminAuthResponse>{
            override fun onResponse(call: Call<AdminAuthResponse>, response: Response<AdminAuthResponse>) {
                if(response.isSuccessful){

                    responseLiveData.value= response.body()
                }
                else{
                    if(response.code() == 401){
                        responseLiveData.value = null
                    }
                }
            }

            override fun onFailure(call: Call<AdminAuthResponse>, t: Throwable) {
                Log.d("tester","Network Error")
                responseLiveData.value = null
            }

        })
        return responseLiveData
    }

    fun init() : Boolean?{
        var resp : Boolean? = null
        authApiService.init().enqueue(object : Callback<AuthInit>{
            override fun onResponse(call: Call<AuthInit>, response: Response<AuthInit>) {
                if(response.isSuccessful){
                    resp = true
                }
            }

            override fun onFailure(p0: Call<AuthInit>, p1: Throwable) {
                resp = false
            }

        })
        return resp
    }
}