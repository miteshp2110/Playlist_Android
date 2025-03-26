package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xceptions.playlist.databinding.FragmentAdminAddmetaBinding
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.Languages.GetLanguagesItem
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.utils.GenreAdapter
import com.xceptions.playlist.utils.LanguageAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.AdminViewModelFactory
import com.xceptions.playlist.viewmodel.GenreViewModel
import com.xceptions.playlist.viewmodel.LanguageViewModel

class AddMetaFragment: Fragment() {

    private var _binding : FragmentAdminAddmetaBinding? = null
    private val binding get() = _binding!!

    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val languageViewModel: LanguageViewModel by viewModels { AdminViewModelFactory(token) }
    private val genreViewModel : GenreViewModel by viewModels { AdminViewModelFactory(token) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminAddmetaBinding.inflate(inflater,container,false)

        val languageRecycler : RecyclerView = binding.languageRecycleView
        languageRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        val genreRecycler : RecyclerView = binding.genreRecycleView
        genreRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        languageViewModel.allLanguagesData.observe(this.viewLifecycleOwner){language ->
            val languageAdapter = LanguageAdapter(language?:GetLanguages())
            languageRecycler.adapter = languageAdapter
            binding.languageProgressBar.visibility=View.GONE
            binding.languageRecycleView.visibility = View.VISIBLE
        }

        genreViewModel.allGenreData.observe(this.viewLifecycleOwner){genre ->
            val genreAdapter = GenreAdapter(genre?: GetGenre())
            genreRecycler.adapter = genreAdapter
            binding.genreProgressBar.visibility = View.GONE
            binding.genreRecycleView.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}