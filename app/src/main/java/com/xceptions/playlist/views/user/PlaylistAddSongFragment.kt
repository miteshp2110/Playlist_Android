package com.xceptions.playlist.views.user

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentCreatePlaylistBinding
import com.xceptions.playlist.databinding.FragmentPlaylistAddSongBinding
import com.xceptions.playlist.utils.AddSongSearchAdapter
import com.xceptions.playlist.utils.EditSongAdapter
import com.xceptions.playlist.utils.SearchResultAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.PlaylistSharedViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory


class PlaylistAddSongFragment : Fragment() {

    private var _binding : FragmentPlaylistAddSongBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: PlaylistSharedViewModel by navGraphViewModels(R.id.playlist_graph) {
        UserViewModelFactory(token)
    }



    private var searchRunnable: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistAddSongBinding.inflate(inflater,container,false)

        binding.playlistName.text = viewModel.createPlayListName
        binding.searchResultRecycler.layoutManager = LinearLayoutManager(this.requireContext())
        binding.addedSongsRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        viewModel.totalDuration.observe(viewLifecycleOwner){response ->
            val dur: Int = response
            val min : Int = dur/60
            val secInt : Int = dur - min*60
            val secStr : String = min.toString()+":"+if(secInt<10){"0"+secInt.toString()}else{secInt.toString()}
            binding.createPlaylistDuration.text = secStr
        }
        viewModel.totalSongs.observe(viewLifecycleOwner){response ->
            binding.createPlaylistTotalSongs.text = response.toString()
        }

        viewModel.songsList.observe(viewLifecycleOwner){response ->
           if(response != null){
               if(response.size == 0){
                   binding.addedSongsRecycler.visibility = View.GONE
                   binding.noTextView.visibility = View.VISIBLE
               }
               else{
                   val adapter = EditSongAdapter(response,viewModel)
                   binding.addedSongsRecycler.adapter = adapter
                   binding.noTextView.visibility = View.GONE
                   binding.addedSongsRecycler.visibility = View.VISIBLE
               }
           }


        }


        viewModel.searchResponse.observe(viewLifecycleOwner){result ->
            if(result!=null){

                val searchAdapter = AddSongSearchAdapter(result,viewModel)
                binding.searchResultRecycler.adapter = searchAdapter
                binding.searchProgress.visibility = View.GONE
                binding.searchResultRecycler.visibility = View.VISIBLE
            }
        }

        binding.searchSongName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchRunnable?.let { handler.removeCallbacks(it) }
                searchRunnable = Runnable {
                    s?.toString()?.let { query ->
                        if (query.isNotEmpty()) {
                            viewModel.searchSong(query)
                            binding.searchProgress.visibility = View.VISIBLE
                        }
                        else{
                            binding.searchResultRecycler.visibility = View.GONE
                        }
                    }
                }
                handler.postDelayed(searchRunnable!!, 500)
            }

        })

        binding.buttonGoBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.doneButton.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }



}