package com.xceptions.playlist.network.user

import com.xceptions.playlist.model.song.GetTrendingSongs
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {

    @GET("song/trending")
    suspend fun getTendingSongs():Response<GetTrendingSongs>
}