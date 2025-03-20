package com.xceptions.playlist.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.xceptions.playlist.BuildConfig


class GoogleSignIn(private val context: Context) {


    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
        .requestEmail()
        .build()
    private val googleSignInClient = GoogleSignIn.getClient(context, gso)


    fun getIntent():Intent{
        return googleSignInClient.signInIntent
    }


    fun handleSignInResult(task: Task<GoogleSignInAccount>,context: Context) {
        Log.d("tester","handle")
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)

            val idToken = account.idToken
            val email = account.email
            val profilePicUri = account.photoUrl

            Log.d("tester", "ID: ${account.id}")
            Log.d("tester", "Email: $email")
            Log.d("tester", "ID Token: $idToken")
            Log.d("tester", "Profile: $profilePicUri")

        } catch (e: ApiException) {
            Log.e("tester", "Sign-In Failed: ${e.statusCode}", e)
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }
    }


}