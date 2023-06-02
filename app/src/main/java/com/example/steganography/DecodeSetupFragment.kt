package com.example.steganography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.steganography.databinding.FragmentDecodeSetupBinding

class DecodeSetupFragment : Fragment(R.layout.fragment_decode_setup){

    private lateinit var binding: FragmentDecodeSetupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDecodeSetupBinding.bind(view)
        binding.start.setOnClickListener { onStartPressed(view) }

    }

    private fun onStartPressed(view: View) {
        view.findNavController().navigate(R.id.action_decodeSetupFragment_to_decodeResultFragment)
    }
}