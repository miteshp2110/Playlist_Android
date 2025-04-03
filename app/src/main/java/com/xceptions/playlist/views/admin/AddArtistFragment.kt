package com.xceptions.playlist.views.admin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xceptions.playlist.databinding.FragmentAdminAddartistBinding
import com.xceptions.playlist.model.artist.GetAllArtist
import com.xceptions.playlist.utils.ArtistAdapter
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.viewmodel.admin.AddArtistViewModel
import com.xceptions.playlist.viewmodel.admin.AdminViewModelFactory

class AddArtistFragment: Fragment() {
    private var _binding : FragmentAdminAddartistBinding? = null
    private val binding get() = _binding!!

    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val addArtistViewModel : AddArtistViewModel by viewModels { AdminViewModelFactory(token) }

    val IMAGE_PICK_CODE = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminAddartistBinding.inflate(inflater,container,false)

        binding.artistRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        addArtistViewModel.allArtists.observe(this.viewLifecycleOwner){data ->
            binding.artistRecycler.adapter = ArtistAdapter(data?: GetAllArtist())
            binding.artistProgressBar.visibility = View.GONE
            binding.artistRecycler.visibility = View.VISIBLE
        }

        binding.selectImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)

        }

        if(addArtistViewModel.imageUri != null){
            binding.imagePreview.setImageURI(addArtistViewModel.imageUri)
        }
        binding.addArtistButton.setOnClickListener {
            addArtistViewModel.artistName = binding.editTextArtistName.text.toString()
            if(addArtistViewModel.artistName != "" && addArtistViewModel.imageUri != null){
                binding.addArtistButton.visibility = View.GONE
                binding.addProgressBar.visibility = View.VISIBLE
                addArtistViewModel.addArtist(this.requireContext(),this.viewLifecycleOwner)
            }
            else{
                Toast.makeText(this.requireContext(),"All Fields are Requied",Toast.LENGTH_SHORT).show()
            }
        }

        addArtistViewModel.addArtistResponse.observe(this.viewLifecycleOwner){response ->
            binding.addProgressBar.visibility = View.GONE
            binding.addArtistButton.visibility = View.VISIBLE
            addArtistViewModel.clear()
            binding.editTextArtistName.setText("")
            binding.imagePreview.setImageURI(null)
            if(response==null){
                Toast.makeText(this.requireContext(),"Failed to Add",Toast.LENGTH_SHORT).show()
            }else
            {
                Toast.makeText(this.requireContext(),response.Message,Toast.LENGTH_SHORT).show()
                addArtistViewModel.getAllArtists()
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    addArtistViewModel.imageUri = data.data
                    binding.imagePreview.setImageURI(addArtistViewModel.imageUri)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}