package com.xceptions.playlist.views.user

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentUserHomeBinding
import com.xceptions.playlist.utils.FavouriteAdapter
import com.xceptions.playlist.utils.OnArtistClickListener
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.utils.TopArtistAdapter
import com.xceptions.playlist.utils.TrendingAdapter
import com.xceptions.playlist.viewmodel.user.UserHomeViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserHomeFragment : Fragment() {

    private var _binding : FragmentUserHomeBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: UserHomeViewModel by viewModels{UserViewModelFactory(token)}

//    private var player: ExoPlayer? = null
//    private lateinit var playerView: PlayerView
//    private var playWhenReady = true
//    private var currentItem = 0
//    private var playbackPosition: Long = 0




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHomeBinding.inflate(inflater,container,false)
        binding.trendingRecycler.layoutManager = LinearLayoutManager(this.requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.artistRecycler.layoutManager = LinearLayoutManager(this.requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.favouriteRecycler.layoutManager = GridLayoutManager(this.requireContext(),2)

        viewModel._trendingSongs.observe(viewLifecycleOwner){response ->
            val trendingAdapter = TrendingAdapter(response!!)
            binding.trendingRecycler.adapter = trendingAdapter
        }

        viewModel._topArtist.observe(viewLifecycleOwner){response ->
            val artistAdapter = TopArtistAdapter(response!!,object : OnArtistClickListener{
                override fun onArtistClick(artistId: Int, artistName: String, artistImage: String) {
                    findNavController().navigate(R.id.action_userHomeFragment_to_artistFragment)
                }

            })
            binding.artistRecycler.adapter = artistAdapter
        }

        viewModel._homeFavourite.observe(viewLifecycleOwner){response ->

            if(response!!.size == 0){
                binding.favouriteRecycler.visibility = View.GONE
                binding.noFavText.visibility = View.VISIBLE

            }
            else{
                val favAdapter = FavouriteAdapter(response)
                binding.favouriteRecycler.adapter = favAdapter

            }
        }

        viewModel.resourceCount.observe(viewLifecycleOwner){response ->
            if(response == 3){
                binding.progressBar.visibility = View.GONE
                binding.mainContent.visibility = View.VISIBLE
            }
        }

//        playerView = binding.playerView
//
//        // **Replace with your actual internet music URI**
//        val musicUriString = "https://playlist-backend.tech/service/stream/listen?id=6"
//        val musicUri = Uri.parse(musicUriString)
//
//        initializePlayer(musicUri)


        return binding.root
    }

//    private fun initializePlayer(mediaUri: Uri) {
//        player = ExoPlayer.Builder(this.requireContext()).build().also { exoPlayer ->
//            playerView.player = exoPlayer
//            val mediaItem = MediaItem.fromUri(mediaUri)
//            exoPlayer.setMediaItem(mediaItem)
//            exoPlayer.playWhenReady = playWhenReady
//            exoPlayer.seekTo(currentItem, playbackPosition)
//            exoPlayer.prepare()
//        }
//    }
//
//    private fun releasePlayer() {
//        player?.let { exoPlayer ->
//            playbackPosition = exoPlayer.currentPosition
//            currentItem = exoPlayer.currentMediaItemIndex
//            playWhenReady = exoPlayer.playWhenReady
//            exoPlayer.release()
//            player = null
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        if (player == null) {
//            // **Replace with your actual internet music URI**
//            val musicUriString = "https://playlist-backend.tech/service/stream/listen?id=6"
//            val musicUri = Uri.parse(musicUriString)
//            initializePlayer(musicUri)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (player != null) {
//            player?.playWhenReady = playWhenReady
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (player != null) {
//            playWhenReady = player!!.playWhenReady
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        releasePlayer()
//    }
}