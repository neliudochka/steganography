package com.example.steganography.fragments.decode

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.algorithms.Steganography
import com.example.steganography.databinding.FragmentDecodeSetupBinding
import com.example.steganography.fragments.encode.EncodeSetupFragmentDirections

class DecodeSetupFragment : Fragment(R.layout.fragment_decode_setup){
    private lateinit var binding: FragmentDecodeSetupBinding
    private var title = "Decode: setup"

    //for image selection
    private var latestUri: Uri? = null
    private val previewImage by lazy { binding.image }
    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts
        .GetContent()) { uri: Uri? -> uri?.let {
        previewImage.setImageURI(uri)
        latestUri = uri
    }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDecodeSetupBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setTitle(title)

        initButtons(view)
    }

    private fun initButtons(view: View) {
        initStartButton(view)
        initLoadPictureButton()
    }

    private fun initStartButton(view: View) {
        binding.start.setOnClickListener {
            val imageUri = latestUri.toString()
            Log.d("d", imageUri)
            if (latestUri == null) {
                Toast.makeText(context, "no entry", Toast.LENGTH_SHORT).show()
            } else {
                val bitmap = BitmapFactory.decodeStream(
                    requireContext().contentResolver.openInputStream(latestUri!!))
                val secretMessage = Steganography().decode(bitmap.copy(bitmap.config, true))
                view.findNavController().navigate(
                    DecodeSetupFragmentDirections.actionDecodeSetupFragmentToDecodeResultFragment(imageUri))
            }
        }
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