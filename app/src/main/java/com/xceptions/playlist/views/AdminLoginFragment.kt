package com.xceptions.playlist.views

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
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
import com.xceptions.playlist.utils.SecurePrefManager
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
        val adminEmail = arguments?.let { AdminLoginFragmentArgs.fromBundle(it).adminEmail }

        val email = binding.adminEmail
        email.setText(adminEmail)
        val pass = binding.adminPassword
        val login = binding.login

        viewModel.loginAdminResponse.observeForever { response ->
            if(response != null){
                SecurePrefManager.saveJwtToken(this.requireContext(),response.token)
                SecurePrefManager.setAdmin(this.requireContext(),true)
                SecurePrefManager.saveUserInfo(this.requireContext(),"",adminEmail!!,"")
                val adminIntent = Intent(this.requireContext(),com.xceptions.playlist.views.admin.AdminActivity::class.java)
                adminIntent.flags = FLAG_ACTIVITY_NEW_TASK
                startActivity(adminIntent)
                if(this.requireContext() is AuthActivity){
                    (this.requireContext() as AuthActivity).finish()
                }
            }
            else{
                Toast.makeText(this.requireContext(),"Wrong Password",Toast.LENGTH_SHORT).show()
            }
        }

        login.setOnClickListener{
            if(binding.adminPassword.text.toString() != ""){
                val req = AdminAuthRequest(email.text.toString(),pass.text.toString())
                viewModel.loginAdmin(req)
            }
            else{
                Toast.makeText(this.requireContext(),"Password Required",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}