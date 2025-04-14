package com.xceptions.playlist.views.user

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
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
import com.xceptions.playlist.utils.OnSongClickListener
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.utils.TopArtistAdapter
import com.xceptions.playlist.utils.TrendingAdapter
import com.xceptions.playlist.viewmodel.user.UserActivityViewModel
import com.xceptions.playlist.viewmodel.user.UserHomeViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserHomeFragment : Fragment() {

    private var _binding : FragmentUserHomeBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: UserHomeViewModel by viewModels{UserViewModelFactory(token)}
    private val viewModelActivity : UserActivityViewModel by activityViewModels{UserViewModelFactory(token)}




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserHomeBinding.inflate(inflater,container,false)
        binding.trendingRecycler.layoutManager = LinearLayoutManager(this.requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.artistRecycler.layoutManager = LinearLayoutManager(this.requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.favouriteRecycler.layoutManager = GridLayoutManager(this.requireContext(),2)
        binding.recentRecycler.layoutManager = GridLayoutManager(this.requireContext(),2)
        viewModel._trendingSongs.observe(viewLifecycleOwner){response ->
            val trendingAdapter = TrendingAdapter(response!!,object : OnSongClickListener{
                override fun onClick(songId: Int) {
                    viewModelActivity.playSong(songId)
                }

            })
            binding.trendingRecycler.adapter = trendingAdapter
        }

        viewModel._topArtist.observe(viewLifecycleOwner){response ->
            val artistAdapter = TopArtistAdapter(response!!,object : OnArtistClickListener{
                override fun onArtistClick(artistId: Int, artistName: String, artistImage: String) {
                    val action = UserHomeFragmentDirections.actionUserHomeFragmentToArtistFragment(artistName,artistImage,artistId)
                    findNavController().navigate(action)
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
                val favAdapter = FavouriteAdapter(response,object : OnSongClickListener{
                    override fun onClick(songId: Int) {
                        viewModelActivity.playSong(songId)
                    }

                })
                binding.favouriteRecycler.adapter = favAdapter

            }
        }
        viewModel._homeFavourite.observe(viewLifecycleOwner){response ->

            if(response!!.size == 0){
                binding.recentRecycler.visibility = View.GONE
                binding.noRecentText.visibility = View.VISIBLE

            }
            else{
                val recentAdapter = FavouriteAdapter(response,object : OnSongClickListener{
                    override fun onClick(songId: Int) {
                        viewModelActivity.playSong(songId)
                    }

                })
                binding.recentRecycler.adapter = recentAdapter

            }
        }

        viewModel.resourceCount.observe(viewLifecycleOwner){response ->
            if(response == 3){
                binding.progressBar.visibility = View.GONE
                binding.mainContent.visibility = View.VISIBLE
            }
        }


        return binding.root
    }

}