package com.xceptions.playlist.views

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.ActivitySplashBinding
import com.xceptions.playlist.utils.SecurePrefManager
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkInternetAndProceed()
    }

    private fun checkInternetAndProceed() {
        if (!isInternetAvailable()) {
            showRetryDialog()
        } else {
            proceedWithTokenLogic()
        }
    }
    private fun proceedWithTokenLogic() {
        val token = SecurePrefManager.getJwtToken(this)
        val isAdmin = SecurePrefManager.isAdmin(this)

        if (token != null) {
            val request = Request.Builder()
                .url("https://playlist-backend.tech/service/auth/init")
                .addHeader("Authorization", "Bearer $token")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    runOnUiThread {
                        if (response.isSuccessful) {
                            // Navigate to Admin or User activity based on the flag.
                            val nextIntent = if (isAdmin) {
                                Intent(this@SplashActivity, com.xceptions.playlist.views.admin.AdminActivity::class.java)
                            } else {
                                Intent(this@SplashActivity, com.xceptions.playlist.views.user.UserActivity::class.java)
                            }
                            startActivity(nextIntent)
                        } else {
                            // If the token is invalid, remove stored details and redirect to AuthActivity.
                            SecurePrefManager.removeAllDetailsFromMemory(this@SplashActivity)
                            startActivity(Intent(this@SplashActivity, com.xceptions.playlist.views.AuthActivity::class.java))
                        }
                        finish()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        SecurePrefManager.removeAllDetailsFromMemory(this@SplashActivity)
                        startActivity(Intent(this@SplashActivity, com.xceptions.playlist.views.AuthActivity::class.java))
                        finish()
                    }
                }
            })
        } else {

            startActivity(Intent(this, com.xceptions.playlist.views.AuthActivity::class.java))
            finish()
        }
    }

    private fun showRetryDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("No Internet Connection")
            .setMessage("Internet is not available. Would you like to retry?")
            .setCancelable(false)
            .setPositiveButton("Retry") { dialog, _ ->
                dialog.dismiss()
                // Re-check connectivity and proceed.
                checkInternetAndProceed()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                finish() // Close the activity if user cancels.
            }
            .show()
    }


    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            networkInfo != null && networkInfo.isConnected
        }
    }
}
