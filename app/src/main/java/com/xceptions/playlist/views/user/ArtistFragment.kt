package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentArtistBinding
import com.xceptions.playlist.databinding.FragmentUserHomeBinding
import com.xceptions.playlist.utils.ArtistSongAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.ArtistViewModel
import com.xceptions.playlist.viewmodel.user.UserHomeViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory
import com.xceptions.playlist.views.AdminLoginFragmentArgs

class ArtistFragment : Fragment() {

    private var _binding : FragmentArtistBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: ArtistViewModel by viewModels{ UserViewModelFactory(token) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater,container,false)

        binding.artistSongsRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        val artistId = arguments?.let { ArtistFragmentArgs.fromBundle(it).artistId }
        val artistName = arguments?.let { ArtistFragmentArgs.fromBundle(it).artistName }
        val artistImage = arguments?.let { ArtistFragmentArgs.fromBundle(it).artistProfile }

        Picasso.get().load(artistImage).into(binding.artistImage)
        binding.artistName.text = artistName

        viewModel.getAllSongsByArtist(artistId!!)

        viewModel.songsByArtist.observe(viewLifecycleOwner){response->
            Log.d("artist",response.toString())
            if(response != null && response.size !=0){
                binding.artistSongsRecycler.adapter = ArtistSongAdapter(response)
                binding.progressBar.visibility = View.GONE
                binding.artistSongsRecycler.visibility = View.VISIBLE
            }
        }

        return binding.root
    }
}