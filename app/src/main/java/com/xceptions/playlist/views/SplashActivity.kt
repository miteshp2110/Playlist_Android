package com.xceptions.playlist.views

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.xceptions.playlist.databinding.ActivitySplashBinding
import com.xceptions.playlist.utils.SecurePrefManager
import okhttp3.*

import java.io.IOException

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val client = OkHttpClient()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        // Delay a little to allow splash to settle
        handler.postDelayed({ checkInternetAndProceed() }, 300)
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
                    handler.post {
                        if (response.isSuccessful) {
                            val nextIntent = if (isAdmin) {
                                Intent(this@SplashActivity, com.xceptions.playlist.views.admin.AdminActivity::class.java)
                            } else {
                                Intent(this@SplashActivity, com.xceptions.playlist.views.user.UserActivity::class.java)
                            }
                            startActivity(nextIntent)
                        } else {
                            SecurePrefManager.removeAllDetailsFromMemory(this@SplashActivity)
                            startActivity(Intent(this@SplashActivity, com.xceptions.playlist.views.AuthActivity::class.java))
                        }
                        finish()
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    handler.post {
                        SecurePrefManager.removeAllDetailsFromMemory(this@SplashActivity)
                        startActivity(Intent(this@SplashActivity, com.xceptions.playlist.views.AuthActivity::class.java))
                        finish()
                    }
                }
            })
        } else {
            handler.post {
                startActivity(Intent(this, com.xceptions.playlist.views.AuthActivity::class.java))
                finish()
            }
        }
    }

    private fun showRetryDialog() {
        handler.post {
            AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Internet is not available. Would you like to retry?")
                .setCancelable(false)
                .setPositiveButton("Retry") { dialog, _ ->
                    dialog.dismiss()
                    checkInternetAndProceed()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                .show()
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            networkInfo != null && networkInfo.isConnected
        }
    }
}
