package com.xceptions.playlist.views.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentUserPlaylistBinding
import com.xceptions.playlist.utils.PlaylistAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserPlaylistViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserPlaylistFragment : Fragment() {

    private var _binding: FragmentUserPlaylistBinding? = null
    private val binding get() = _binding!!

    private val token: String by lazy {
        SecurePrefManager.getJwtToken(requireContext()) ?: "null"
    }

    private val viewModel: UserPlaylistViewModel by viewModels {
        UserViewModelFactory(token)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initial state
        binding.playlistProgressBar.visibility = View.VISIBLE
        binding.playlistRecycler.visibility = View.GONE
        binding.noListText.visibility = View.GONE

        // Observe before fetching
        viewModel.getAllPlaylistResponse.observe(viewLifecycleOwner) { response ->
            binding.playlistProgressBar.visibility = View.GONE

            if (response.isNullOrEmpty()) {
                binding.playlistRecycler.visibility = View.GONE
                binding.noListText.visibility = View.VISIBLE
            } else {
                binding.noListText.visibility = View.GONE
                binding.playlistRecycler.visibility = View.VISIBLE
                binding.playlistRecycler.layoutManager = LinearLayoutManager(requireContext())
                binding.playlistRecycler.adapter = PlaylistAdapter(response)
            }
        }

        // Trigger playlist fetch
        viewModel.getAllPlaylist()

        // Button click navigation
        binding.createPlaylistButton.setOnClickListener {
            findNavController().navigate(R.id.action_userPlaylistFragment_to_playlist_graph)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
