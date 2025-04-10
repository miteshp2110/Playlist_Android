package com.xceptions.playlist.views.user

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.ActivityUserBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserActivityViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var navController: NavController
    private val token: String by lazy { SecurePrefManager.getJwtToken(this) ?: "null" }
    private val viewModel : UserActivityViewModel by viewModels{UserViewModelFactory(token)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val miniPlayer = binding.miniplayer

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        if (navHostFragment != null) {
            navController = navHostFragment.navController
        } else {
            throw IllegalStateException("NavHostFragment not found")
        }

        if (savedInstanceState == null) {
            navController.setGraph(R.navigation.nav_graph)
        }

        binding.navHome.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.userHomeFragment)
            setActiveNavItem(binding.navHome.id)
            viewModel.setActiveFragment(binding.navHome.id)
        }

        binding.navSearch.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.userSearchFragment)
            setActiveNavItem(binding.navSearch.id)
            viewModel.setActiveFragment(binding.navSearch.id)
        }

        binding.navPlaylist.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.userPlaylistFragment)
            setActiveNavItem(binding.navPlaylist.id)
            viewModel.setActiveFragment(binding.navPlaylist.id)
        }

        binding.navAccount.setOnClickListener {
            navController.popBackStack()
            navController.navigate(R.id.userAccountFragment)
            setActiveNavItem(binding.navAccount.id)
            viewModel.setActiveFragment(binding.navAccount.id)
        }


        // miniplayer stuff


        viewModel.currentSongId.observe(this){response ->
            if(response!=null){
                binding.miniplayerSongName.text = response.name
                binding.miniplayerArtistName.text = response.artist
                Picasso.get().load(response.song_image_url).into(binding.miniplayerSongImage)
                binding.miniplayer.visibility = View.VISIBLE
            }
        }



        setActiveNavItem(viewModel.activeItemId!!)



    }

    private fun setActiveNavItem(selectedItemId: Int) {
        binding.navHome.isSelected = binding.navHome.id == selectedItemId
        binding.navSearch.isSelected = binding.navSearch.id == selectedItemId
        binding.navPlaylist.isSelected = binding.navPlaylist.id == selectedItemId
        binding.navAccount.isSelected = binding.navAccount.id == selectedItemId
    }
}
