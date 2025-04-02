package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.databinding.FragmentUserSearchBinding
import com.xceptions.playlist.utils.SearchResultAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserSearchViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserSearchFragment : Fragment() {


    private var _binding : FragmentUserSearchBinding? =  null
    private val binding get() = _binding!!
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val viewModel: UserSearchViewModel by viewModels{UserViewModelFactory(token)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentUserSearchBinding.inflate(inflater,container,false)

        binding.searchRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        viewModel.searchResponse.observe(viewLifecycleOwner){result ->
            if(result!=null){
                val searchAdapter = SearchResultAdapter(result)
                binding.searchRecycler.adapter = searchAdapter
            }
        }
        viewModel.searchSong("a")

        return binding.root
    }
}