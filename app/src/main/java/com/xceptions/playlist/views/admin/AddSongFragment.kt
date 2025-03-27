package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xceptions.playlist.databinding.FragmentAdminAddsongBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.AddSongViewModel
import com.xceptions.playlist.viewmodel.AdminViewModelFactory

class AddSongFragment: Fragment() {

    private var _binding : FragmentAdminAddsongBinding? = null
    private val binding get() = _binding!!
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val addSongViewModel : AddSongViewModel by viewModels { AdminViewModelFactory(token) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAddsongBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSongViewModel.allSongsResponse.observe(this.viewLifecycleOwner){data ->
            Log.d("addsongs","in view $data")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}