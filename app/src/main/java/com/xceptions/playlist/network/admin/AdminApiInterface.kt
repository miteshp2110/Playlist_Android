package com.xceptions.playlist.network.admin

import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.MessageResponse
import com.xceptions.playlist.model.NameRequestBody
import com.xceptions.playlist.model.genre.GetGenre
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AdminApiInterface {

    @GET("languages")
    suspend fun getAllLanguages():Response<GetLanguages>

    @GET("genere")
    suspend fun getAllGenre():Response<GetGenre>

    @POST("languages")
    fun addLanguage(@Body request : NameRequestBody) : Call<MessageResponse>
}