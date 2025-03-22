package com.xceptions.playlist.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.xceptions.playlist.BuildConfig
import com.xceptions.playlist.model.userauth.AuthRequest
import androidx.navigation.findNavController
import com.xceptions.playlist.R
import com.xceptions.playlist.repository.AuthRepository
import com.xceptions.playlist.views.AuthActivity
import com.xceptions.playlist.views.GetStartedFragmentDirections


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
                        if(context is AuthActivity){
                            val action = GetStartedFragmentDirections.actionGetStartedToAdminLogin(response.email)
                            context.findNavController(R.id.nav_host_auth).navigate(action)
                        }
                    }
                    else{
                        // Send to Home Activity
                        SecurePrefManager.saveJwtToken(context,response.token)
                        SecurePrefManager.saveUserInfo(context, response.name,response.email,response.profile_url)
                        val userIntent = Intent(context,com.xceptions.playlist.views.user.UserActivity::class.java)
                        userIntent.flags = FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(userIntent)

                        if(context is AuthActivity){
                            context.finish()
                        }
                    }
                }

            }





        } catch (e: ApiException) {
//            Log.e("tester", "Sign-In Failed: ${e.statusCode}", e)
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        }

    }


}