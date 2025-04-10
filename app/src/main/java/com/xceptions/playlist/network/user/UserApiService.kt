package com.xceptions.playlist.network.user

import com.xceptions.playlist.model.MessageResponse
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.favourites.GetAllFavourites
import com.xceptions.playlist.model.favourites.AddFavourite
import com.xceptions.playlist.model.favourites.RemoveFavourite
import com.xceptions.playlist.model.playlist.CreatePlaylist
import com.xceptions.playlist.model.playlist.GetAllPlaylist
import com.xceptions.playlist.model.song.GetSongsByArtist
import com.xceptions.playlist.model.song.GetTrendingSongs
import com.xceptions.playlist.model.song.MiniPlayerSong
import com.xceptions.playlist.model.song.SearchSongs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("favourite/add")
    fun addFavourite(@Body songId: AddFavourite) : Call<MessageResponse>

    @POST("favourite/remove")
    fun removeSongFromFav(@Body favId : RemoveFavourite) : Call<MessageResponse>

    @GET("playlist/get")
    suspend fun getAllPlaylist() : Response<GetAllPlaylist>

    @POST("playlist/add")
    fun addPlaylist(@Body request : CreatePlaylist) : Call<MessageResponse>

    @GET("favourite/all")
    suspend fun getAllFavourite(@Query("limit")limit:Int = 999) : Response<GetAllFavourites>

    @GET("song/artist/")
    suspend fun getSongsByArtist(@Query("id") id:Int) : Response<GetSongsByArtist>

    @GET("song/player/")
    suspend fun getSongById(@Query("id")id:Int): Response<MiniPlayerSong>
}
