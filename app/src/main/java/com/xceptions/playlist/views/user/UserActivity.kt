package com.xceptions.playlist.views.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.squareup.picasso.Picasso
import com.xceptions.playlist.BuildConfig
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.ActivityUserBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.MusicPlayerService
import com.xceptions.playlist.viewmodel.user.UserActivityViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var navController: NavController
    private val token: String by lazy { SecurePrefManager.getJwtToken(this) ?: "null" }
    private val viewModel : UserActivityViewModel by viewModels{UserViewModelFactory(token)}

    override fun onBackPressed() {
        if(binding.mainLayout.currentState==R.id.end){
            binding.mainLayout.transitionToStart()
        }
        else{
            super.onBackPressed()
        }
    }

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


        viewModel.currentSongId.observe(this) { response ->
            if (response != null) {
                val songUrl = BuildConfig.BASE_URL+"stream/listen?id="+response.id
                val startMusicIntent = Intent(this,MusicPlayerService::class.java).apply {
                    action = MusicPlayerService.ACTION_PLAY
                    putExtra(MusicPlayerService.EXTRA_SONG_URL,songUrl)
                    putExtra(MusicPlayerService.EXTRA_BEARER_TOKEN,token)
                }
                ContextCompat.startForegroundService(this,startMusicIntent)
                binding.miniplayerPlayPauseButton.setImageResource(R.drawable.icon_pause)
                binding.miniplayerPlayPauseButton.isClickable=true
                viewModel.isPlaying = true
                binding.miniplayerSongName.text = response.name
                binding.miniplayerArtistName.text = response.artist
                Picasso.get().load(response.song_image_url).into(binding.miniplayerSongImage)
                binding.extendedPlayerSongName.text = response.name
                binding.extendedPlayerArtistName.text = response.artist
            }
        }
        binding.extendedPlayerPlayPause.setOnClickListener {
            togglePlayOrPause()
        }

        binding.miniplayerPlayPauseButton.setOnClickListener {
            if(binding.miniplayerPlayPauseButton.isClickable){
                togglePlayOrPause()
            }
        }

        setActiveNavItem(viewModel.activeItemId!!)

        binding.extendedPlayerPrevious.setOnClickListener {
            viewModel.prevSong()

        }
        binding.extendedPlayerNext.setOnClickListener {
            viewModel.nextSong()
        }



    }

    private fun togglePlayOrPause(){
        if(viewModel.isPlaying){
            val pauseMusicIntent = Intent(this,MusicPlayerService::class.java).apply {
                action = MusicPlayerService.ACTION_PAUSE
            }
            ContextCompat.startForegroundService(this,pauseMusicIntent)
            viewModel.isPlaying = false
            binding.miniplayerPlayPauseButton.setImageResource(R.drawable.icon_play)
            binding.extendedPlayerPlayPause.setImageResource(R.drawable.icon_play)
        }
        else{
            val playPausedSong = Intent(this,MusicPlayerService::class.java).apply {
                action = MusicPlayerService.ACTION_PLAY_SONG
            }
            ContextCompat.startForegroundService(this,playPausedSong)
            binding.extendedPlayerPlayPause.setImageResource(R.drawable.icon_pause)
            viewModel.isPlaying = true
            binding.miniplayerPlayPauseButton.setImageResource(R.drawable.icon_pause)
        }
    }



    private fun setActiveNavItem(selectedItemId: Int) {
        binding.navHome.isSelected = binding.navHome.id == selectedItemId
        binding.navSearch.isSelected = binding.navSearch.id == selectedItemId
        binding.navPlaylist.isSelected = binding.navPlaylist.id == selectedItemId
        binding.navAccount.isSelected = binding.navAccount.id == selectedItemId
    }

}
