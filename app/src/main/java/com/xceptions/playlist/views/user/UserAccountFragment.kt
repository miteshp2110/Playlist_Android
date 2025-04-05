package com.xceptions.playlist.views.user

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentUserAccountBinding
import com.xceptions.playlist.utils.GoogleSignIn
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.user.UserAccountViewModel
import com.xceptions.playlist.viewmodel.user.UserViewModelFactory

class UserAccountFragment : Fragment() {

    private var _binding : FragmentUserAccountBinding? = null
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val binding get() = _binding!!
    private val viewModel: UserAccountViewModel by viewModels{ UserViewModelFactory(token) }
    private lateinit var gso : GoogleSignIn


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAccountBinding.inflate(inflater,container,false)

        binding.userName.text = SecurePrefManager.getUserInfo(this.requireContext()).get("name")
        binding.userEmail.text = SecurePrefManager.getUserInfo(this.requireContext()).get("email")
        Picasso.get().load(SecurePrefManager.getUserInfo(this.requireContext()).get("profile_url")).into(binding.userImage)
        gso = GoogleSignIn(this.requireContext())
        binding.favouriteButton.setOnClickListener {
            findNavController().navigate(R.id.action_to_fav)
        }

        binding.settingsButton.setOnClickListener {
            Toast.makeText(this.requireContext(),"Yet to Implement",Toast.LENGTH_SHORT).show()
        }
        binding.settingsButton.setOnClickListener {
            Toast.makeText(this.requireContext(),"Yet to Implement",Toast.LENGTH_SHORT).show()
        }
        binding.themeButton.setOnClickListener {
            Toast.makeText(this.requireContext(),"Yet to Implement",Toast.LENGTH_SHORT).show()
        }
        binding.aboutButton.setOnClickListener {
            Toast.makeText(this.requireContext(),"Yet to Implement",Toast.LENGTH_SHORT).show()
        }

        binding.buttonLogout.setOnClickListener {
            gso.logout(this.requireContext())
        }

        return binding.root
    }
}