package com.xceptions.playlist.views.admin

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xceptions.playlist.R
import com.xceptions.playlist.databinding.FragmentAdminAddsongBinding
import com.xceptions.playlist.model.Languages.GetLanguages
import com.xceptions.playlist.model.song.GetAllSongs
import com.xceptions.playlist.utils.SecurePrefManager
import com.xceptions.playlist.utils.SongsAdapter
import com.xceptions.playlist.viewmodel.AddSongViewModel
import com.xceptions.playlist.viewmodel.AdminViewModelFactory

class AddSongFragment: Fragment() {

    private var _binding : FragmentAdminAddsongBinding? = null
    private val binding get() = _binding!!
    private val token: String by lazy { SecurePrefManager.getJwtToken(requireContext()) ?: "null" }
    private val addSongViewModel : AddSongViewModel by viewModels { AdminViewModelFactory(token) }

    val IMAGE_PICK_CODE = 1000
    val SONG_PICK_CODE = 1001

    var resourceCount = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminAddsongBinding.inflate(inflater,container,false)

        val songRecycler : RecyclerView = binding.songRecyclerView
        songRecycler.layoutManager = LinearLayoutManager(this.requireContext())

        addSongViewModel.allSongsResponse.observe(this.viewLifecycleOwner){data ->
            binding.songRecyclerView.visibility = View.VISIBLE
            val songAdapter  = SongsAdapter(data?: GetAllSongs())
            songRecycler.adapter = songAdapter
            val size = data?.size?:10
            binding.buttonLoadMore.visibility = View.VISIBLE
            if(size%10 != 0){
                binding.buttonLoadMore.visibility = View.GONE
            }
            binding.songsProgressBar.visibility = View.GONE
        }

        binding.buttonLoadMore.setOnClickListener {
            addSongViewModel.loadSongs()
            binding.songsProgressBar.visibility = View.VISIBLE

        }

        if(addSongViewModel.imageUri!=null){

            binding.imagePreview.setImageURI(addSongViewModel.imageUri)


        }

        binding.selectImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_CODE)

        }
        binding.selectSongBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(intent, SONG_PICK_CODE)
        }

        addSongViewModel.languagesList.observe(this.viewLifecycleOwner){data->
            resourceCount++
            if(resourceCount == 3){
                binding.mainProgressBar.visibility = View.GONE
                binding.mainLayout.visibility = View.VISIBLE
            }
            var langList :MutableList<String> = mutableListOf()
            data?.forEach { language ->
                langList.add(language.name)
            }
            val languageAdapter = ArrayAdapter(this.requireContext(),R.layout.spinner_item,langList)
            languageAdapter.setDropDownViewResource(R.layout.spinner_item)
            binding.languageSpinner.adapter = languageAdapter

            binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    addSongViewModel.languageId = data?.get(position)?.id.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        }

        addSongViewModel.genreList.observe(this.viewLifecycleOwner){data->
            resourceCount++
            if(resourceCount == 3){
                binding.mainProgressBar.visibility = View.GONE
                binding.mainLayout.visibility = View.VISIBLE
            }
            var genList :MutableList<String> = mutableListOf()
            data?.forEach { language ->
                genList.add(language.name)
            }
            val genreAdapter = ArrayAdapter(this.requireContext(),R.layout.spinner_item,genList)
            genreAdapter.setDropDownViewResource(R.layout.spinner_item)
            binding.genreSpinner.adapter = genreAdapter

            binding.genreSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    addSongViewModel.genereId = data?.get(position)?.id.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        }

        addSongViewModel.artistAll.observe(this.viewLifecycleOwner){data->
            resourceCount++
            if(resourceCount == 3){
                binding.mainProgressBar.visibility = View.GONE
                binding.mainLayout.visibility = View.VISIBLE
            }
            var artistList :MutableList<String> = mutableListOf()
            data?.forEach { artist ->
                artistList.add(artist.name)
            }
            val artistAdapter = ArrayAdapter(this.requireContext(),R.layout.spinner_item,artistList)
            artistAdapter.setDropDownViewResource(R.layout.spinner_item)
            binding.artistSpinner.adapter = artistAdapter

            binding.artistSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    addSongViewModel.artistId = data?.get(position)?.id.toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        }

        if(addSongViewModel.songName!=null){
            binding.editTextSongName.setText(addSongViewModel.songName)
        }



        binding.uploadBtn.setOnClickListener {
            addSongViewModel.songName = binding.editTextSongName.text.toString()
            if(addSongViewModel.imageUri != null && addSongViewModel.songUri != null && addSongViewModel.songName!= null && addSongViewModel.artistId!=null
                && addSongViewModel.languageId!=null && addSongViewModel.genereId!=null){
                binding.uploadBtn.visibility = View.GONE
                binding.uploadProgressBar.visibility = View.VISIBLE
                addSongViewModel.addSong(this.requireContext(),this.viewLifecycleOwner)
            }
            else{
                Toast.makeText(this.requireContext(),"Add All Fields",Toast.LENGTH_SHORT).show()
            }
        }

        addSongViewModel.addSongResponse.observe(this.viewLifecycleOwner){response ->
            if (response!= null){
                binding.uploadProgressBar.visibility = View.GONE
                binding.uploadBtn.visibility = View.VISIBLE

                binding.editTextSongName.setText("")
                addSongViewModel.clearFields()
                binding.imagePreview.setImageURI(null)
                binding.songNameText.text = "No Song Selected"
                binding.languageSpinner.setSelection(0)
                binding.genreSpinner.setSelection(0)
                binding.artistSpinner.setSelection(0)
                Toast.makeText(this.requireContext() , "Added Song",Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    addSongViewModel.imageUri = data.data
                    binding.imagePreview.setImageURI(addSongViewModel.imageUri)
                }
                SONG_PICK_CODE -> {
                    addSongViewModel.songUri = data.data
                    val songName = getFileName(this.requireContext(),addSongViewModel.songUri!!)
                    binding.songNameText.text = "Selected: $songName"
                }
            }
        }
    }

    fun getFileName(context: Context, uri: Uri): String {
        var name = "Unknown"
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) {
                    name = it.getString(index)
                }
            }
        }
        return name
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}