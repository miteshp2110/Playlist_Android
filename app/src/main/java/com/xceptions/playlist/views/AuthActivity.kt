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

class AuthActivity : AppCompatActivity() {



    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val token = SecurePrefManager.getJwtToken(this)
        val isAdmin = SecurePrefManager.isAdmin(this)
        if(token != null){
            if(isAdmin){
                startActivity(Intent(this,com.xceptions.playlist.views.admin.AdminActivity::class.java))
                finish()
                return
            }
            startActivity(Intent(this,com.xceptions.playlist.views.user.UserActivity::class.java))
            finish()
            return
        }

        setContentView(binding.root)


    }

}