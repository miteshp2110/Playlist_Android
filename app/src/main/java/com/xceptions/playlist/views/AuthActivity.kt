package com.xceptions.playlist.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.xceptions.playlist.databinding.ActivityAuthBinding
import com.xceptions.playlist.utils.GoogleSignIn
import com.xceptions.playlist.utils.SecurePrefManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class AuthActivity : AppCompatActivity() {



    private lateinit var binding : ActivityAuthBinding
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val token = SecurePrefManager.getJwtToken(this)
        val isAdmin = SecurePrefManager.isAdmin(this)
        val cntxn = this
        if(token != null){

            val request = Request.Builder()

                .url("https://playlist-backend.tech/service/auth/init")
                .addHeader("Authorization","Bearer $token")
                .build()
            client.newCall(request).enqueue(object : Callback{
                override fun onResponse(call: Call, response: Response) {
                    if(response.isSuccessful){
                        if(isAdmin){
                            startActivity(Intent(cntxn,com.xceptions.playlist.views.admin.AdminActivity::class.java))
                            finish()
                            return
                        }
                        startActivity(Intent(cntxn,com.xceptions.playlist.views.user.UserActivity::class.java))
                        finish()
                        return
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    SecurePrefManager.removeAllDetailsFromMemory(cntxn)
                }
            })
        }

        setContentView(binding.root)


    }

}