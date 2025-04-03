package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xceptions.playlist.databinding.FragmentUserAccountBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserAccountViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserAccountFragment : Fragment() {

    private var _binding : FragmentUserAccountBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: UserAccountViewModel by viewModels{ UserViewModelFactory(token) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAccountBinding.inflate(inflater,container,false)
        return binding.root
    }
}