package com.example.steganography.fragments.decode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.steganography.MainActivity
import com.example.steganography.R

class DecodeResultFragment : Fragment(R.layout.fragment_decode_result){
    private var title = "Decode: result"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setTitle(title)
    }
}