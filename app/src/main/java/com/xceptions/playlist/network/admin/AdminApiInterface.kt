package com.xceptions.playlist.network.admin

import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.MessageResponse
import com.xceptions.playlist.model.NameRequestBody
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.model.song.GetAllSongs
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AdminApiInterface {

    @GET("languages")
    suspend fun getAllLanguages():Response<GetLanguages>

    @GET("genere")
    suspend fun getAllGenre():Response<GetGenre>

    @POST("languages")
    fun addLanguage(@Body request : NameRequestBody) : Call<MessageResponse>

    @POST("genere")
    fun addGenre(@Body request: NameRequestBody) : Call<MessageResponse>

    @GET("song/all/mobile")
    suspend fun getAllSongs(@Query("page") page: Int) : Response<GetAllSongs>

    @Multipart
    @POST("song")
    fun addSong(
        @Part("name") name: RequestBody,
        @Part("language") language: RequestBody,
        @Part("genere") genre: RequestBody,
        @Part("artist") artist: RequestBody,
        @Part song_image: MultipartBody.Part,
        @Part song: MultipartBody.Part
    ):Call<MessageResponse>

    @GET("artists")
    suspend fun getAllArtists():Response<GetAllArtist>

    @Multipart
    @POST("artists")
    fun addArtist(
        @Part("name") name : RequestBody,
        @Part profile_image : MultipartBody.Part
    ): Call<MessageResponse>

}