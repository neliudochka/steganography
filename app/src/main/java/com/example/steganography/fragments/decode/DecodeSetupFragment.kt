package com.example.steganography.fragments.decode

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.databinding.FragmentDecodeSetupBinding

class DecodeSetupFragment : Fragment(R.layout.fragment_decode_setup){
    private lateinit var binding: FragmentDecodeSetupBinding
    private var title = "Decode: setup"

    private val previewImage by lazy { binding.image }
    private val selectImageFromGalleryResult = registerForActivityResult(
        ActivityResultContracts
        .GetContent()) { uri: Uri? -> uri?.let { previewImage.setImageURI(uri) }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDecodeSetupBinding.bind(view)
        binding.start.setOnClickListener { onStartPressed(view) }
        (activity as MainActivity).supportActionBar?.setTitle(title)

        initLoadPictureButton()
    }

    private fun onStartPressed(view: View) {
        view.findNavController().navigate(R.id.action_decodeSetupFragment_to_decodeResultFragment)
    }

    private fun initLoadPictureButton() {
        binding.buttonLoadPicture.setOnClickListener {
            selectImageFromGallery() }
    }

    private fun selectImageFromGallery() {
        selectImageFromGalleryResult.launch("image/*")
    }

}