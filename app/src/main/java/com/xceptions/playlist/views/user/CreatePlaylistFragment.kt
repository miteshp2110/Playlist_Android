package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentCreatePlaylistBinding
import com.xceptions.playlist.databinding.FragmentUserPlaylistBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.CreatePlaylistViewModel
import com.xceptions.playlist.viewmodel.user.UserPlaylistViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class CreatePlaylistFragment : Fragment() {

    private var _binding : FragmentCreatePlaylistBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: CreatePlaylistViewModel by viewModels{ UserViewModelFactory(token) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater,container,false)
        return binding.root
    }
}