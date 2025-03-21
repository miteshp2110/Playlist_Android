package com.xceptions.playlist.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.xceptions.playlist.databinding.FragmentAdminLoginBinding
import com.xceptions.playlist.model.adminauth.AdminAuthRequest
import com.xceptions.playlist.viewmodel.AdminLoginViewModel

class AdminLoginFragment : Fragment() {
    private var _binding : FragmentAdminLoginBinding? = null
    private val binding get() = _binding!!
    val viewModel : AdminLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminLoginBinding.inflate(inflater,container,false)
        val email = binding.adminEmail
        val pass = binding.adminPassword
        val login = binding.login

        viewModel.loginAdminResponse.observeForever { response ->
            if(response != null){
                Log.d("tester",response.token)
                Toast.makeText(this.requireContext(),response.token,Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d("tester","failed")
            }
        }

        login.setOnClickListener{
            val req = AdminAuthRequest(email.text.toString(),pass.text.toString())
            viewModel.loginAdmin(req)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}