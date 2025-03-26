package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xceptions.playlist.databinding.FragmentAdminAddmetaBinding
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.Languages.GetLanguagesItem
import com.xceptions.playlist.model.NameRequestBody
import com.xceptions.playlist.model.genre.GetGenre
import com.xceptions.playlist.utils.GenreAdapter
import com.xceptions.playlist.utils.LanguageAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.AddMetaViewModel
import com.xceptions.playlist.viewmodel.AdminViewModelFactory

class AddMetaFragment: Fragment() {

    private var _binding : FragmentAdminAddmetaBinding? = null
    private val binding get() = _binding!!

    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val addMetaViewModel : AddMetaViewModel by viewModels { AdminViewModelFactory(token) }

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

        addMetaViewModel.allLanguagesData.observe(this.viewLifecycleOwner){language ->
            val languageAdapter = LanguageAdapter(language?:GetLanguages())
            languageRecycler.adapter = languageAdapter
            binding.languageProgressBar.visibility=View.GONE
            binding.languageRecycleView.visibility = View.VISIBLE
        }

        addMetaViewModel.allGenreData.observe(this.viewLifecycleOwner){genre ->
            val genreAdapter = GenreAdapter(genre?: GetGenre())
            genreRecycler.adapter = genreAdapter
            binding.genreProgressBar.visibility = View.GONE
            binding.genreRecycleView.visibility = View.VISIBLE
        }
        addMetaViewModel.addLanguageResponse.observe(this.viewLifecycleOwner){response ->
            if(response == null){
                Toast.makeText(this.requireContext(),"Failed to add",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this.requireContext(),"Added New Language",Toast.LENGTH_SHORT).show()
                addMetaViewModel.getAllLanguages()

            }
        }

        binding.addLanguageButton.setOnClickListener {
            val newLanguage : String = binding.editTextLanguageName.text.toString()
            if(newLanguage == ""){

                Toast.makeText(this.requireContext(),"Language Cannot be Empty",Toast.LENGTH_SHORT).show()
            }
            addMetaViewModel.addLanguage(NameRequestBody(newLanguage),this.viewLifecycleOwner)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}