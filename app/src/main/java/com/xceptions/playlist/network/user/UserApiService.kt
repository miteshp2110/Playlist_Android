package com.xceptions.playlist.network.user

import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.model.song.SearchSongs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApiService {

    @GET("song/trending")
    suspend fun getTendingSongs():Response<GetTrendingSongs>

    @GET("artists/top")
    suspend fun getTopArtists(): Response<GetAllArtist>

    @GET("favourite/all")
    suspend fun getHomeFavourite() : Response<GetAllFavourites>


    @GET("song/search")
    suspend fun searchSong(@Query("name")name:String) : Response<SearchSongs>
}
