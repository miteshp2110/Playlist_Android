package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xceptions.playlist.databinding.FragmentAdminAccountBinding
import com.xceptions.playlist.utils.GoogleSignIn
import com.xceptions.playlist.utils.SecurePrefManager

class AccountFragment : Fragment() {

    private var _binding : FragmentAdminAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var gso: GoogleSignIn


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAccountBinding.inflate(inflater,container,false)

        gso = GoogleSignIn(this.requireContext())

        binding.logoutButton.setOnClickListener {
            gso.logout(this.requireContext())
        }
        binding.adminEmail.setText(SecurePrefManager.getUserInfo(this.requireContext()).get("email"))



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}