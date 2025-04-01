package com.xceptions.playlist.views.user

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xceptions.playlist.databinding.ActivityUserBinding
import com.xceptions.playlist.viewmodel.admin.AdminActivityViewModel

class UserActivity: AppCompatActivity() {

    private lateinit var binding : ActivityUserBinding

    private val viewModel : AdminActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.activeFragment.observe(this){ fragment ->
            replaceFragment(fragment)
        }

        viewModel.activeItemId.observe(this){ id ->
            setActiveNavItem(id)

        }

        if(viewModel.activeItemId.value == -1){

            binding.navHome.isSelected = true
            viewModel.setActiveFragment(UserHomeFragment(),binding.navHome.id)
        }

        binding.navSearch.setOnClickListener {
            viewModel.setActiveFragment(UserSearchFragment(), binding.navSearch.id)
        }
        binding.navPlaylist.setOnClickListener {
            viewModel.setActiveFragment(UserPlaylistFragment(), binding.navPlaylist.id)
        }
        binding.navHome.setOnClickListener {
            viewModel.setActiveFragment(UserHomeFragment(), binding.navHome.id)
        }
        binding.navAccount.setOnClickListener {
            viewModel.setActiveFragment(UserAccountFragment(), binding.navAccount.id)
        }


    }




    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,fragment)
            .commit()
    }

    private fun setActiveNavItem(selectedItemId: Int) {
        binding.navHome.isSelected = binding.navHome.id == selectedItemId
        binding.navSearch.isSelected = binding.navSearch.id == selectedItemId
        binding.navPlaylist.isSelected = binding.navPlaylist.id == selectedItemId
        binding.navAccount.isSelected = binding.navAccount.id == selectedItemId
    }



}