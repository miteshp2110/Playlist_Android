package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xceptions.playlist.databinding.FragmentAdminAddsongBinding

class AddSongFragment: Fragment() {

    private var _binding : FragmentAdminAddsongBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAddsongBinding.inflate(inflater,container,false)


        return binding.root
    }
}