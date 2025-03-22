package com.xceptions.playlist.utils

import android.content.Context
import android.media.session.MediaSession.Token
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.xceptions.playlist.BuildConfig
import org.json.JSONObject

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
    private fun deleteJwtToken(context: Context){
        getSharedPrefs(context).edit().remove("JWT_TOKEN").apply()
    }
    fun saveUserInfo(context: Context,name:String,email:String,profile_url:String){
        val userInfoJson = JSONObject().apply {
            put("name", name)
            put("email", email)
            put("profile_url", profile_url)
        }.toString()

        getSharedPrefs(context).edit().putString("userInfo", userInfoJson).apply()
    }
    fun getUserInfo(context: Context): Map<String, String?> {
        val sharedPrefs = getSharedPrefs(context)
        val userInfoJson = sharedPrefs.getString("userInfo", null)

        return if (userInfoJson != null) {
            val jsonObject = JSONObject(userInfoJson)
            mapOf(
                "name" to jsonObject.optString("name", ""),
                "email" to jsonObject.optString("email", ""),
                "profile_url" to jsonObject.optString("profile_url", "")
            )
        } else {
            emptyMap()
        }
    }
    private fun deleteUserInfo(context: Context){
        getSharedPrefs(context).edit().remove("userInfo").apply()
    }
    fun setAdmin(context: Context,value:Boolean){
        getSharedPrefs(context).edit().putBoolean("isAdmin",true).apply()
    }
    fun isAdmin(context: Context){
        getSharedPrefs(context).getBoolean("isAdmin",false)
    }
    private fun deleteAdmin(context: Context){
        getSharedPrefs(context).edit().remove("isAdmin").apply()
    }



    fun removeAllDetailsFromMemory(context: Context){
        deleteJwtToken(context)
        deleteUserInfo(context)
        deleteAdmin(context)
    }


}