package com.example.steganography.fragments.encode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.R
import com.example.steganography.databinding.FragmentEncodeSetupBinding

class EncodeSetupFragment : Fragment(R.layout.fragment_encode_setup){

    private lateinit var binding: FragmentEncodeSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeSetupBinding.bind(view)
        binding.start.setOnClickListener { onStartPressed(view) }

    }

    private fun onStartPressed(view: View) {
        view.findNavController().navigate(R.id.action_encodeSetupFragment_to_encodeResultFragment)
    }
}