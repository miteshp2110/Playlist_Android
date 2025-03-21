package com.xceptions.playlist.views

import android.os.Bundle
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
//        setContentView(R.layout.activity_auth)

//        gso = GoogleSignIn(this)
//        val button : Button = findViewById(R.id.auth)
//        progressBar= findViewById(R.id.progressBar)

//        button.setOnClickListener{
//            progressBar.visibility = View.VISIBLE
//            startActivityForResult(gso.getIntent(),100)
//        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        progressBar.visibility = View.GONE
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 100){
//
//            val task : Task<GoogleSignInAccount> = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
//            gso.handleSignInResult(task,this)
//        }
//    }
}