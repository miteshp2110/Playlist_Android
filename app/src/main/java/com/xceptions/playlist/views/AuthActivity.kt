package com.xceptions.playlist.views

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.xceptions.playlist.databinding.ActivityAuthBinding
import com.xceptions.playlist.utils.GoogleSignIn

class AuthActivity : AppCompatActivity() {



    private lateinit var binding : ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)


        setContentView(binding.root)


    }

}