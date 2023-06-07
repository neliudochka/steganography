package com.example.steganography.fragments.decode

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.databinding.FragmentDecodeResultBinding
import com.example.steganography.fragments.encode.EncodeResultFragmentArgs


class DecodeResultFragment : Fragment(R.layout.fragment_decode_result){
    private lateinit var binding: FragmentDecodeResultBinding
    private var title = "Decode: result"

    private val args : DecodeResultFragmentArgs by navArgs()
    private val previewImage by lazy { binding.image }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDecodeResultBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setTitle(title)

        binding.toSetup.setOnClickListener { onToSetupPressed(view) }
        setArgs()
    }

    private fun setArgs() {
        val imageUri = Uri.parse(args.imageUri)
        previewImage.setImageURI(imageUri)
    }
    private fun onToSetupPressed(view: View) {
        view.findNavController().navigate(R.id.action_decodeResultFragment_to_decodeSetupFragment)
    }
}