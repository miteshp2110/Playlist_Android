package com.xceptions.playlist.views.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentCreatePlaylistBinding
import com.xceptions.playlist.databinding.FragmentPlaylistAddSongBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.PlaylistSharedViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory


class PlaylistAddSongFragment : Fragment() {

    private var _binding : FragmentPlaylistAddSongBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: PlaylistSharedViewModel by activityViewModels{ UserViewModelFactory(token) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistAddSongBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        return binding.root
    }
}