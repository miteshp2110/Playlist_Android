package com.xceptions.playlist.utils

import android.content.Context
import android.media.session.MediaSession.Token
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.xceptions.playlist.BuildConfig

object SecurePrefManager {
    private const val PREFS_FILE_NAME = BuildConfig.TOKEN_KEY


    private fun getSharedPrefs(context: Context) = EncryptedSharedPreferences.create(
        context,
        PREFS_FILE_NAME,
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveJwtToken(context: Context,token: String){
        getSharedPrefs(context).edit().putString("JWT_TOKEN",token).apply()
    }
    fun getJwtToken(context: Context):String?{
        return getSharedPrefs(context).getString("JWT_TOKEN",null)
    }
    fun deleteJwtToken(context: Context){
        getSharedPrefs(context).edit().remove("JWT_TOKEN").apply()
    }
    fun saveUserInfo(context: Context,name:String,email:String,profile_url:String){
        getSharedPrefs(context).edit().putStringSet("userInfo",mutableSetOf(name,email,profile_url))
    }
    fun getUserInfo(context: Context): MutableSet<String>? {
        return getSharedPrefs(context).getStringSet("userInfo",null)
    }
    fun deleteUserInfo(context: Context){
        getSharedPrefs(context).edit().remove("userInfo")
    }

    fun removeAllDetailsFromMemory(context: Context){
        deleteJwtToken(context)
        deleteUserInfo(context)
    }


}