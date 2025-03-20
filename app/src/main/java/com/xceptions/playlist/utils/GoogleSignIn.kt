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
import com.xceptions.playlist.model.userauth.AuthRequest
import androidx.activity.viewModels
import com.xceptions.playlist.repository.AuthRepository
import com.xceptions.playlist.viewmodel.AuthViewModel


class GoogleSignIn(private val context: Context) {

    private val authRepository:AuthRepository =AuthRepository()
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

            val request = AuthRequest(idToken.toString())

            authRepository.authenticateUser(request).observeForever{response ->
                if(response==null){
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                }
                else{
                    if(response.Message=="isAdmin"){
                        // Send to Admin login fragment
                        Toast.makeText(context,"User is admin",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        // Send to Home Activity
                        Toast.makeText(context,"Welcome ${response.name}",Toast.LENGTH_SHORT).show()

                    }
                }

            }





        } catch (e: ApiException) {
//            Log.e("tester", "Sign-In Failed: ${e.statusCode}", e)
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }
    }


}