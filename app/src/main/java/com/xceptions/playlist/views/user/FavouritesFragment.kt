package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentFavouritesBinding
import com.xceptions.playlist.databinding.FragmentUserAccountBinding
import com.xceptions.playlist.utils.AllFavAdapter
import com.xceptions.playlist.utils.FavouriteAdapter
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
        _binding = FragmentFavouritesBinding.inflate(inflater,container,false)

        binding.favouriteSongsRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        viewModel.allFavData.observe(viewLifecycleOwner){response ->
            if(response == null){
                binding.searchProgress.visibility = View.GONE
                binding.noTextView.visibility = View.VISIBLE
                binding.favouriteSongsRecycler.visibility = View.GONE
            }
            else{
                if(response.size == 0){
                    binding.searchProgress.visibility = View.GONE
                    binding.noTextView.visibility = View.VISIBLE
                    binding.favouriteSongsRecycler.visibility = View.GONE
                }
                else{
                    val adapter = AllFavAdapter(response,viewModel)

                    binding.favouriteSongsRecycler.adapter = adapter
                    binding.searchProgress.visibility = View.GONE
                    binding.noTextView.visibility = View.GONE
                    binding.favouriteSongsRecycler.visibility = View.VISIBLE
                }
            }
        }
        return binding.root
    }
}