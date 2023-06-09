package com.example.steganography.fragments.encode

import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.BuildConfig
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.algorithms.Pixel
import com.example.steganography.algorithms.Steganography
import com.example.steganography.databinding.FragmentEncodeSetupBinding
import java.io.File

class EncodeSetupFragment : Fragment(R.layout.fragment_encode_setup){
    private lateinit var binding: FragmentEncodeSetupBinding
    private var title = "Encode: setup"

    //for image selection
    private var latestUri: Uri? = null
    private val previewImage by lazy { binding.image }
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts
        .GetContent()) { uri: Uri? -> uri?.let {
        previewImage.setImageURI(uri)
        latestUri = uri
        }
    }

    //for taking picture
    private val takeImageResult = registerForActivityResult(ActivityResultContracts
        .TakePicture()) { isSuccess -> if (isSuccess) {  latestUri?.let { uri ->
        previewImage.setImageURI(uri) }
    }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeSetupBinding.bind(view)
        (activity as MainActivity ).supportActionBar?.setTitle(title)

        initButtons(view)
    }

    private fun initButtons(view: View) {
        initStartButton(view)
        initLoadPictureButton()
        initTakeAPictureButton()
    }
    private fun initStartButton(view: View) {
        binding.start.setOnClickListener {
            val message = binding.text.text.toString()
            val imageUri = latestUri.toString()
            if (message == "" || latestUri == null) {
                Toast.makeText(context, "no entry", Toast.LENGTH_SHORT).show()
            } else {
                val bitmap = BitmapFactory.decodeStream(
                    requireContext().contentResolver.openInputStream(latestUri!!))
                //розкоментуєш як знайдеш помилку
                val finalImage = Steganography().encode(message, bitmap)

                view.findNavController().navigate(
                    EncodeSetupFragmentDirections
                        .actionEncodeSetupFragmentToEncodeResultFragment(message, imageUri))

            }
        }
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
            latestUri = uri
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
