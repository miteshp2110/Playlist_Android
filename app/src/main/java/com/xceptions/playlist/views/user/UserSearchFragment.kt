package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
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
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.databinding.FragmentUserSearchBinding
import com.xceptions.playlist.model.song.SearchSongs
import com.xceptions.playlist.utils.OnSongClickListener
import com.xceptions.playlist.utils.SearchResultAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserActivityViewModel
import com.xceptions.playlist.viewmodel.user.UserSearchViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserSearchFragment : Fragment() {


    private var _binding : FragmentUserSearchBinding? =  null
    private val binding get() = _binding!!
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val viewModel: UserSearchViewModel by viewModels{UserViewModelFactory(token)}
    private var searchRunnable: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())
    private val viewModelActivity : UserActivityViewModel by activityViewModels{UserViewModelFactory(token)}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentUserSearchBinding.inflate(inflater,container,false)

        binding.searchRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        viewModel.searchResponse.observe(viewLifecycleOwner){result ->
            if(result!=null){
                if(result.size == 0){
                    binding.searchRecycler.visibility = View.GONE
                    binding.searchProgress.visibility = View.GONE
                    binding.beforeSearch.visibility = View.VISIBLE
                }
                else{
                    val searchAdapter = SearchResultAdapter(result,viewModel,object : OnSongClickListener{
                        override fun onClick(songId: Int) {
                            viewModelActivity.playSong(songId)
                        }

                    })
                    binding.searchRecycler.adapter = searchAdapter
                    binding.searchProgress.visibility = View.GONE
                    binding.beforeSearch.visibility = View.GONE
                    binding.searchRecycler.visibility = View.VISIBLE
                }

            }
        }

        binding.searchSongName.addTextChangedListener(object : TextWatcher{
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
                            binding.beforeSearch.visibility = View.GONE
                            binding.searchProgress.visibility = View.VISIBLE

                        }
                        else{
                            binding.beforeSearch.visibility = View.VISIBLE
                            binding.searchRecycler.visibility = View.GONE
                        }
                    }
                }
                handler.postDelayed(searchRunnable!!, 500)
            }

        })

        return binding.root
    }
}