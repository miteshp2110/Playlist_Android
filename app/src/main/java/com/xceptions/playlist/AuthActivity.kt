package com.xceptions.playlist

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.xceptions.playlist.utils.GoogleSignIn

class AuthActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignIn
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        gso = GoogleSignIn(this)
        val button : Button = findViewById(R.id.auth)
        progressBar= findViewById(R.id.progressBar)

        button.setOnClickListener{
            progressBar.visibility = View.VISIBLE
            startActivityForResult(gso.getIntent(),100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        progressBar.visibility = View.GONE
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){

            val task : Task<GoogleSignInAccount> = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            gso.handleSignInResult(task,this)
        }
    }
}