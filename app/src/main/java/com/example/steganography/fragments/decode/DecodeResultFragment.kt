package com.example.steganography.fragments.decode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.databinding.FragmentDecodeResultBinding


class DecodeResultFragment : Fragment(R.layout.fragment_decode_result){
    private lateinit var binding: FragmentDecodeResultBinding
    private var title = "Decode: result"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDecodeResultBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setTitle(title)

        binding.toSetup.setOnClickListener { onToSetupPressed(view) }
    }

    private fun onToSetupPressed(view: View) {
        view.findNavController().navigate(R.id.action_decodeResultFragment_to_decodeSetupFragment)
    }
}