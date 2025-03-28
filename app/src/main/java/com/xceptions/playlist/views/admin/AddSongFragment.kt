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
//    var imageUri: Uri? = null
//    var songUri: Uri? = null


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
            binding.selectImageBtn.visibility = View.GONE
            binding.imagePreview.setImageURI(addSongViewModel.imageUri)
            binding.imagePreview.visibility = ImageView.VISIBLE

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
            var langList :MutableList<String> = mutableListOf()
            data?.forEach { language ->
                langList.add(language.name)
            }
            val languageAdapter = ArrayAdapter(this.requireContext(),android.R.layout.simple_spinner_dropdown_item,langList)
            binding.languageSpinner.adapter = languageAdapter

            binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    addSongViewModel.languageId = data?.get(position)?.id.toString()
                    Log.d("addsongs", addSongViewModel.languageId!!)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

//        val allLanguages = addSongViewModel.allLanguages
//        val languages = listOf()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                IMAGE_PICK_CODE -> {
                    addSongViewModel.imageUri = data.data
                    binding.imagePreview.setImageURI(addSongViewModel.imageUri)
                    binding.imagePreview.visibility = ImageView.VISIBLE
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