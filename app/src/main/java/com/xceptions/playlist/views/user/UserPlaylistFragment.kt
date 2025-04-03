package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentUserPlaylistBinding
import com.xceptions.playlist.utils.PlaylistAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserPlaylistViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserPlaylistFragment : Fragment() {

    private var _binding : FragmentUserPlaylistBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: UserPlaylistViewModel by viewModels{ UserViewModelFactory(token) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPlaylistBinding.inflate(inflater,container,false)


        binding.playlistRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        viewModel.getAllPlaylistResponse.observe(viewLifecycleOwner){response ->
            if (response == null){
                binding.playlistProgressBar.visibility = View.GONE
                binding.noListText.visibility = View.VISIBLE
            }
            else{
                val playlistAdapter = PlaylistAdapter(response)
                binding.playlistRecycler.adapter = playlistAdapter
                binding.playlistProgressBar.visibility = View.GONE
                binding.playlistRecycler.visibility = View.VISIBLE
            }
        }

        binding.createPlaylistButton.setOnClickListener {

            view?.findNavController()?.navigate(R.id.action_userPlaylistFragment_to_createPlaylistFragment)

        }


        return binding.root
    }

}