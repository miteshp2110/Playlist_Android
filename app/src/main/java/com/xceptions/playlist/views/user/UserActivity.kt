package com.xceptions.playlist.views.user

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

class UserActivity : AppCompatActivity(), MusicPlayerService.OnProgressUpdateListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var navController: NavController
    private val token: String by lazy { SecurePrefManager.getJwtToken(this) ?: "null" }
    private val viewModel : UserActivityViewModel by viewModels{UserViewModelFactory(token)}

    // Music service binding
    private var musicService: MusicPlayerService? = null
    private var isBound = false
    private var isUserSeeking = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MusicPlayerService.MusicBinder
            musicService = binder.getService()
            isBound = true

            // Register for progress updates
            musicService?.setOnProgressUpdateListener(this@UserActivity)

            // Initialize UI with current state
            updatePlayPauseButtons()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
            isBound = false
        }
    }

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

        setupNavigation()
        setupMusicControls()
        setupSeekBar()

        // Bind to the music service
        Intent(this, MusicPlayerService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }

        setActiveNavItem(viewModel.activeItemId!!)
    }

    private fun setupNavigation() {
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
    }

    private fun setupMusicControls() {
        viewModel.currentSongId.observe(this) { response ->
            if (response != null) {
                val songUrl = BuildConfig.BASE_URL+"stream/listen?id="+response.id
                val startMusicIntent = Intent(this, MusicPlayerService::class.java).apply {
                    action = MusicPlayerService.ACTION_PLAY
                    putExtra(MusicPlayerService.EXTRA_SONG_URL, songUrl)
                    putExtra(MusicPlayerService.EXTRA_BEARER_TOKEN, token)
                }
                ContextCompat.startForegroundService(this, startMusicIntent)
                binding.miniplayerPlayPauseButton.setImageResource(R.drawable.icon_pause)
                binding.miniplayerPlayPauseButton.isClickable = true
                viewModel.isPlaying = true
                updatePlayPauseButtons()
                binding.miniplayerSongName.text = response.name
                binding.miniplayerArtistName.text = response.artist
                Picasso.get().load(response.song_image_url).into(binding.miniplayerSongImage)
                binding.extendedPlayerSongName.text = response.name
                binding.extendedPlayerArtistName.text = response.artist

                // Reset seekbar when new song starts
                binding.extendedPlayerSeekBar.progress = 0
                binding.miniplayerSeekBar.progress = 0
            }
        }

        binding.extendedPlayerPlayPause.setOnClickListener {
            togglePlayOrPause()
        }

        binding.miniplayerPlayPauseButton.setOnClickListener {
            if(binding.miniplayerPlayPauseButton.isClickable) {
                togglePlayOrPause()
            }
        }

        binding.extendedPlayerPrevious.setOnClickListener {
            viewModel.prevSong()
        }

        binding.extendedPlayerNext.setOnClickListener {
            viewModel.nextSong()
        }
    }

    private fun setupSeekBar() {
        binding.extendedPlayerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    musicService?.seekTo(it.progress)
                }
                isUserSeeking = false
            }
        })
        binding.miniplayerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                isUserSeeking = true
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    musicService?.seekTo(it.progress)
                }
                isUserSeeking = false
            }
        })
    }

    private fun togglePlayOrPause() {
        if(viewModel.isPlaying) {
            val pauseMusicIntent = Intent(this, MusicPlayerService::class.java).apply {
                action = MusicPlayerService.ACTION_PAUSE
            }
            ContextCompat.startForegroundService(this, pauseMusicIntent)
            viewModel.isPlaying = false
            updatePlayPauseButtons()
        }
        else {
            val playPausedSong = Intent(this, MusicPlayerService::class.java).apply {
                action = MusicPlayerService.ACTION_PLAY_SONG
            }
            ContextCompat.startForegroundService(this, playPausedSong)
            viewModel.isPlaying = true
            updatePlayPauseButtons()
        }
    }

    private fun updatePlayPauseButtons() {
        val iconRes = if (viewModel.isPlaying) R.drawable.icon_pause else R.drawable.icon_play
        binding.miniplayerPlayPauseButton.setImageResource(iconRes)
        binding.extendedPlayerPlayPause.setImageResource(iconRes)
    }



    // Callback from service when progress updates
    override fun onProgressUpdate(progress: Int, duration: Int) {
        if (!isUserSeeking) {
            binding.extendedPlayerSeekBar.progress = progress
            binding.miniplayerSeekBar.progress = progress


            // Update max duration if it changed
            if (binding.extendedPlayerSeekBar.max != duration) {
                binding.extendedPlayerSeekBar.max = duration
            }
            if (binding.miniplayerSeekBar.max != duration) {
                binding.miniplayerSeekBar.max = duration
            }
        }
    }

    private fun setActiveNavItem(selectedItemId: Int) {
        binding.navHome.isSelected = binding.navHome.id == selectedItemId
        binding.navSearch.isSelected = binding.navSearch.id == selectedItemId
        binding.navPlaylist.isSelected = binding.navPlaylist.id == selectedItemId
        binding.navAccount.isSelected = binding.navAccount.id == selectedItemId
    }

    override fun onDestroy() {
        if (isBound) {
            musicService?.setOnProgressUpdateListener(null)
            unbindService(serviceConnection)
            isBound = false
        }
        super.onDestroy()
    }
}