package com.example.steganography.fragments.encode

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.steganography.BuildConfig
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.databinding.FragmentEncodeSetupBinding
import java.io.File

class EncodeSetupFragment : Fragment(R.layout.fragment_encode_setup){
    private lateinit var binding: FragmentEncodeSetupBinding
    private var title = "Encode: setup"

    //for image selection
    private val previewImage by lazy { binding.image }
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts
        .GetContent()) { uri: Uri? -> uri?.let { previewImage.setImageURI(uri) }
    }

    //for taking picture
    private var latestTmpUri: Uri? = null
    private val takeImageResult = registerForActivityResult(ActivityResultContracts
        .TakePicture()) { isSuccess -> if (isSuccess) {  latestTmpUri?.let { uri ->
        previewImage.setImageURI(uri) }
    }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeSetupBinding.bind(view)
        binding.start.setOnClickListener { onStartPressed(view) }
        (activity as MainActivity ).supportActionBar?.setTitle(title)

        initLoadPictureButton()
        initTakeAPictureButton()
    }
    private fun onStartPressed(view: View) {
        view.findNavController().navigate(R.id.action_encodeSetupFragment_to_encodeResultFragment)
    }

    private fun initLoadPictureButton() {
        binding.buttonLoadPicture.setOnClickListener {
            selectImageFromGallery() }
    }

    private fun selectImageFromGallery() {
        selectImageFromGalleryResult.launch("image/*")
    }

    private fun initTakeAPictureButton() {
        binding.buttonTakeAPicture.setOnClickListener { takeAPicture() }
    }

    private fun takeAPicture() {
        getTmpFileUri().let { uri ->
            latestTmpUri = uri
            takeImageResult.launch(uri)
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpImage = File.createTempFile("tmp_image_file", ".png", requireContext().cacheDir).apply {
            createNewFile()
        }
        try {
            return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpImage)
        } finally {
            tmpImage.delete()
        }
    }
}