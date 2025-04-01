package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentUserHomeBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.utils.TrendingAdapter
import com.xceptions.playlist.viewmodel.user.UserHomeViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserHomeFragment : Fragment() {

    private var _binding : FragmentUserHomeBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: UserHomeViewModel by viewModels{UserViewModelFactory(token)}


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHomeBinding.inflate(inflater,container,false)
        binding.trendingRecycler.layoutManager = LinearLayoutManager(this.requireContext(),LinearLayoutManager.HORIZONTAL,false)

        viewModel._trendingSongs.observe(viewLifecycleOwner){response ->
            val trendingAdapter = TrendingAdapter(response!!)
            binding.trendingRecycler.adapter = trendingAdapter
        }

        return binding.root
    }
}