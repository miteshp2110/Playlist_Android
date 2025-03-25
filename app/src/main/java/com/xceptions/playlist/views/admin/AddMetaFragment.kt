package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xceptions.playlist.databinding.FragmentAdminAddmetaBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.AdminViewModelFactory
import com.xceptions.playlist.viewmodel.LanguageViewModel

class AddMetaFragment: Fragment() {

    private var _binding : FragmentAdminAddmetaBinding? = null
    private val binding get() = _binding!!

    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val languageViewModel: LanguageViewModel by viewModels { AdminViewModelFactory(token) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminAddmetaBinding.inflate(inflater,container,false)


        languageViewModel.allLanguagesData.observe(this.viewLifecycleOwner){language ->
            Log.d("languages",language.toString())
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}