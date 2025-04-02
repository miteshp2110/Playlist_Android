package com.xceptions.playlist.network.user

import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.model.song.GetTrendingSongs
import retrofit2.Response
import retrofit2.http.GET

interface UserApiService {

    @GET("song/trending")
    suspend fun getTendingSongs():Response<GetTrendingSongs>

    @GET("artists/top")
    suspend fun getTopArtists(): Response<GetAllArtist>

    @GET("favourite/all")
    suspend fun getHomeFavourite() : Response<GetAllFavourites>

}
