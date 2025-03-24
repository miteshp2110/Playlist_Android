package com.xceptions.playlist.views.admin

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navAddsong.isSelected = true
        replaceFragment(AddSongFragment())

        binding.navAddsong.setOnClickListener {
            setActiveNavItem(binding.navAddsong)
            replaceFragment(AddSongFragment())
        }
        binding.navAddartist.setOnClickListener {
            setActiveNavItem(binding.navAddartist)
            replaceFragment(AddArtistFragment())
        }
        binding.navMeta.setOnClickListener {
            setActiveNavItem(binding.navMeta)
            replaceFragment(AddMetaFragment())
        }
        binding.navAccount.setOnClickListener {
            setActiveNavItem(binding.navAccount)
            replaceFragment(AccountFragment())
        }
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id,fragment)
            .commit()
    }

    private fun setActiveNavItem(selectedItem: LinearLayout){
        binding.navAddsong.isSelected = false
        binding.navAddartist.isSelected = false
        binding.navMeta.isSelected = false
        binding.navAccount.isSelected = false

        selectedItem.isSelected = true
    }
}