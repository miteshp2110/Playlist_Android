package com.xceptions.playlist.network.admin

import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.genre.GetGenre
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header

interface AdminApiInterface {

    @GET("languages")
    suspend fun getAllLanguages():Response<GetLanguages>

    @GET("genere")
    suspend fun getAllGenre():Response<GetGenre>
}