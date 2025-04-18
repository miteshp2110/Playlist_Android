package com.xceptions.playlist.repository

import RetrofitClient
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.MessageResponse
import com.xceptions.playlist.model.NameRequestBody
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.model.song.GetAllSongsItem
import com.xceptions.playlist.network.admin.AdminApiInterface
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AdminRepository(token:String) {

    private val apiService : AdminApiInterface = RetrofitClient.getClient(token).create(AdminApiInterface::class.java)

    private val _allLanguages = MutableLiveData<GetLanguages?>()
    val allLanguages :LiveData<GetLanguages?> = _allLanguages

    private val _allGenre = MutableLiveData<GetGenre?>()
    val allGenre :LiveData<GetGenre?> = _allGenre

    private val _allSongs = MutableLiveData<GetAllSongs?>()
    val allSongs : LiveData<GetAllSongs?> = _allSongs

    private val _allArtists = MutableLiveData<GetAllArtist?>()
    val allArtists : LiveData<GetAllArtist?> = _allArtists



    suspend fun getAllLanguages(){
        try {

            val response : Response<GetLanguages> = apiService.getAllLanguages()
            if(response.isSuccessful){
                _allLanguages.postValue(response.body())
            }
            else{
                _allLanguages.postValue(null)
            }
        }
        catch (e:Exception){
            _allLanguages.postValue(null)
        }
    }

    suspend fun getAllGenre(){
        try{
            val response : Response<GetGenre> = apiService.getAllGenre()
            if(response.isSuccessful){
                _allGenre.postValue(response.body())
            }
            else{
                _allGenre.postValue(null)
            }

        }
        catch(e:Exception){
            _allGenre.postValue(null)
        }
    }

    fun addLanguage(body : NameRequestBody):LiveData<MessageResponse?>{
        val responseLiveData = MutableLiveData<MessageResponse?>()

        apiService.addLanguage(body).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if(response.isSuccessful){
                    responseLiveData.value = response.body()
                }
                else{
                    responseLiveData.value = null
                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.d("addLanguage","err: ${t.message}")
                responseLiveData.value = null
            }

        })
        return responseLiveData
    }

    fun addGenre(body : NameRequestBody):LiveData<MessageResponse?>{
        val responseLiveData = MutableLiveData<MessageResponse?>()

        apiService.addGenre(body).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if(response.isSuccessful){
                    responseLiveData.value = response.body()
                }
                else{
                    responseLiveData.value = null
                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.d("addGenre","err: ${t.message}")
                responseLiveData.value = null
            }

        })
        return responseLiveData
    }

    suspend fun getAllSongs(page:Int){

        try{
            val response : Response<GetAllSongs> = apiService.getAllSongs(page)
            if(response.isSuccessful){
                if(_allSongs.value == null){

                    _allSongs.postValue(response.body())
                }
                else{
                    val current :GetAllSongs? = _allSongs.value
                    response.body()?.forEach{song ->
                        current?.add(song)
                    }
                    _allSongs.value = current
                }
            }
            else{
                _allSongs.postValue(null)
            }
        }
        catch(e:Exception){
            _allSongs.postValue(null)
        }
    }

    fun addSong(name: String, languageId: String, genreId: String, artistId: String, imageUri: Uri, songUri: Uri,context: Context):LiveData<MessageResponse?>{

        val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val languageBody = RequestBody.create("text/plain".toMediaTypeOrNull(), languageId)
        val genreBody = RequestBody.create("text/plain".toMediaTypeOrNull(), genreId)
        val artistBody = RequestBody.create("text/plain".toMediaTypeOrNull(), artistId)

        val imageFile = uriToFile(context,imageUri)
        val requestFileImage = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
        val imagePart = MultipartBody.Part.createFormData("song_image", imageFile.name, requestFileImage)

        val songFile = uriToFile(context,songUri)
        val requestFileSong = RequestBody.create("audio/mpeg".toMediaTypeOrNull(), songFile)
        val songPart = MultipartBody.Part.createFormData("song", songFile.name, requestFileSong)

        val addSongsResponse = MutableLiveData<MessageResponse?>()

        apiService.addSong(nameBody,languageBody,genreBody,artistBody,imagePart,songPart).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if(response.isSuccessful){
                    addSongsResponse.value = response.body()
                }
                else{
                    addSongsResponse.value = null
                }

            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.d("addsongs","err: ${t.message}")
                addSongsResponse.value = null
            }

        })
        return addSongsResponse
    }

    fun addArtist(name:String,imageUri: Uri,context: Context) : LiveData<MessageResponse?>{
        val addArtistResponse = MutableLiveData<MessageResponse?>()

        val nameBody = RequestBody.create("text/plain".toMediaTypeOrNull(),name)
        val imageFile = uriToFile(context,imageUri)
        val requestFileImage = RequestBody.create("image/*".toMediaTypeOrNull(),imageFile)
        val imagePart = MultipartBody.Part.createFormData("profile_image",imageFile.name,requestFileImage)

        apiService.addArtist(nameBody,imagePart).enqueue(object : Callback<MessageResponse>{
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if(response.isSuccessful){
                    addArtistResponse.value = response.body()
                }
                else{
                    addArtistResponse.value = null
                }
            }

            override fun onFailure(p0: Call<MessageResponse>, p1: Throwable) {
                addArtistResponse.value = null
            }

        })
        return addArtistResponse
    }

    suspend fun getAllArtists(){
        try{
            val response : Response<GetAllArtist> = apiService.getAllArtists()
            if(response.isSuccessful){
                _allArtists.postValue(response.body())
            }
            else{
                _allArtists.postValue(null)
            }
        }
        catch (e:Exception){
            _allArtists.postValue(null)
        }
    }

    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp", null, context.cacheDir)
        if (inputStream != null) {
            tempFile.outputStream().use { output ->
                inputStream.copyTo(output)
            }
        }
        return tempFile
    }


}
