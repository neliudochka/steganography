package com.example.steganography.fragments.encode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.databinding.FragmentEncodeResultBinding

class EncodeResultFragment : Fragment(R.layout.fragment_encode_result){
    private lateinit var binding: FragmentEncodeResultBinding
    private var title = "Encode: result"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeResultBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setTitle(title)

        binding.toSetup.setOnClickListener { onToSetupPressed(view) }
    }

    private fun onToSetupPressed(view: View) {
        view.findNavController().navigate(R.id.action_encodeResultFragment_to_encodeSetupFragment)
    }
}