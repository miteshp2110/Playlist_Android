package com.xceptions.playlist.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.xceptions.playlist.databinding.FragmentGetStartedBinding
import com.xceptions.playlist.utils.GoogleSignIn

class GetStartedFragment : Fragment() {
    private var _binding : FragmentGetStartedBinding? = null
    private val binding get() = _binding!!

    private lateinit var gso: GoogleSignIn
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetStartedBinding.inflate(inflater,container,false)

        gso = GoogleSignIn(this.requireContext())
        progressBar = binding.progressBar


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        progressBar.visibility = View.GONE
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100){

            val task : Task<GoogleSignInAccount> = com.google.android.gms.auth.api.signin.GoogleSignIn.getSignedInAccountFromIntent(data)
            gso.handleSignInResult(task,this.requireContext())
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button : Button = binding.auth

        button.setOnClickListener{
            progressBar.visibility=View.VISIBLE
            startActivityForResult(gso.getIntent(),100)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding=null
    }
}
