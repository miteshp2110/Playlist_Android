package com.xceptions.playlist.views.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            navController.navigate(R.id.userHomeFragment)
            setActiveNavItem(binding.navHome.id)
        }
        binding.navSearch.setOnClickListener {
            navController.navigate(R.id.userSearchFragment)
            setActiveNavItem(binding.navSearch.id)
        }
        binding.navPlaylist.setOnClickListener {
            navController.navigate(R.id.userPlaylistFragment)
            setActiveNavItem(binding.navPlaylist.id)
        }
        binding.navAccount.setOnClickListener {
            navController.navigate(R.id.userAccountFragment)
            setActiveNavItem(binding.navAccount.id)
        }

        setActiveNavItem(binding.navHome.id)
    }

    private fun setActiveNavItem(selectedItemId: Int) {
        binding.navHome.isSelected = binding.navHome.id == selectedItemId
        binding.navSearch.isSelected = binding.navSearch.id == selectedItemId
        binding.navPlaylist.isSelected = binding.navPlaylist.id == selectedItemId
        binding.navAccount.isSelected = binding.navAccount.id == selectedItemId
    }
}
