package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentFavouritesBinding
import com.xceptions.playlist.databinding.FragmentUserAccountBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.FavouritesViewModel
import com.xceptions.playlist.viewmodel.user.UserAccountViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class FavouritesFragment : Fragment() {

    private var _binding : FragmentFavouritesBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: FavouritesViewModel by viewModels{ UserViewModelFactory(token) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }
}