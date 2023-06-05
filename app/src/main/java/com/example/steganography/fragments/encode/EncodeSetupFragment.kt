package com.example.steganography.fragments.encode

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.databinding.FragmentEncodeSetupBinding

class EncodeSetupFragment : Fragment(R.layout.fragment_encode_setup){
    private lateinit var binding: FragmentEncodeSetupBinding
    private var title = "Encode: setup"

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeSetupBinding.bind(view)
        binding.start.setOnClickListener { onStartPressed(view) }
        (activity as MainActivity ).supportActionBar?.setTitle(title)

        initButtonsListeners()
    }
    private fun onStartPressed(view: View) {
        view.findNavController().navigate(R.id.action_encodeSetupFragment_to_encodeResultFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.image.setImageURI(imageUri)
        }
    }

    private fun initButtonsListeners() {
        binding.buttonLoadPicture.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)  }
    }
}