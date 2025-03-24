package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.ActivityAdminBinding
import com.xceptions.playlist.viewmodel.AdminActivityViewModel

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    private val viewModel : AdminActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.activeFragment.observe(this){ fragment ->
            replaceFragment(fragment)
        }

        viewModel.activeItemId.observe(this){ id ->
            setActiveNavItem(id)

        }

        if(viewModel.activeItemId.value == -1){

            binding.navAddsong.isSelected = true
            viewModel.setActiveFragment(AddSongFragment(),binding.navAddsong.id)
        }

        binding.navAddsong.setOnClickListener {
            viewModel.setActiveFragment(AddSongFragment(), binding.navAddsong.id)
        }
        binding.navAddartist.setOnClickListener {
            viewModel.setActiveFragment(AddArtistFragment(), binding.navAddartist.id)
        }
        binding.navMeta.setOnClickListener {
            viewModel.setActiveFragment(AddMetaFragment(), binding.navMeta.id)
        }
        binding.navAccount.setOnClickListener {
            viewModel.setActiveFragment(AccountFragment(), binding.navAccount.id)
        }
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,fragment)
            .commit()
    }

    private fun setActiveNavItem(selectedItemId: Int) {
        binding.navAddsong.isSelected = binding.navAddsong.id == selectedItemId
        binding.navAddartist.isSelected = binding.navAddartist.id == selectedItemId
        binding.navMeta.isSelected = binding.navMeta.id == selectedItemId
        binding.navAccount.isSelected = binding.navAccount.id == selectedItemId
    }
}