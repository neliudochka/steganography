package com.example.steganography.fragments.encode

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.algorithms.Steganography
import com.example.steganography.databinding.FragmentEncodeResultBinding


class EncodeResultFragment : Fragment(R.layout.fragment_encode_result){
    private lateinit var binding: FragmentEncodeResultBinding
    private var title = "Encode: result"

    private val args : EncodeResultFragmentArgs by navArgs()
    private val previewImage by lazy { binding.image }

    lateinit var message: String
    lateinit var imageUri: Uri
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeResultBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setTitle(title)


        setArgs()
        initButtons(view)
    }

    private fun setArgs() {
        // Receive the arguments in a variable
        message = this.args.message
        imageUri = Uri.parse(this.args.imageUri)
        // set the values to respective textViews
        binding.text.text = message
        previewImage.setImageURI(imageUri)
    }

    private fun initButtons(view: View) {
        initToSetup(view)
    }

    private fun initToSetup(view: View) {
        binding.toSetup.setOnClickListener {
            view.findNavController().navigate(R.id.action_encodeResultFragment_to_encodeSetupFragment)
        }
    }
}