package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentCreatePlaylistBinding
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.PlaylistSharedViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class CreatePlaylistFragment : Fragment() {

    private var _binding : FragmentCreatePlaylistBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: PlaylistSharedViewModel by activityViewModels{ UserViewModelFactory(token) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePlaylistBinding.inflate(inflater,container,false)
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object :OnBackPressedCallback(true){
//            override fun handleOnBackPressed() {
//
////                findNavController().navigate(R.id.action)
//            }
//
//        })
        viewModel.totalSongs.observe(viewLifecycleOwner){response ->
            binding.createPlaylistTotalSongs.text = response.toString()
        }
        viewModel.totalDuration.observe(viewLifecycleOwner){response ->
            binding.createPlaylistDuration.text = response.toString()
        }
        binding.saveButton.setOnClickListener {
            viewModel.createPlayListName = binding.createPlaylistName.text.toString()
            if(viewModel.createPlayListName == "" || viewModel.songsList.value?.size == 0){
                Toast.makeText(this.requireContext(),"All Fields Required",Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d("playlist","${viewModel.createPlayListName} ${viewModel.songsList}")
            }
        }

        binding.addSongButton.setOnClickListener {
            viewModel.createPlayListName = binding.createPlaylistName.text.toString()
            if(viewModel.createPlayListName == ""){
                Toast.makeText(this.requireContext(),"Enter Name First",Toast.LENGTH_SHORT).show()
            }
            else{
                findNavController().navigate(R.id.action_createPlaylistFragment_to_playlistAddSongFragment)
            }
        }

        binding.buttonGoBack.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}