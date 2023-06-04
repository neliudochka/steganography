package com.example.steganography.fragments.encode

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.steganography.MainActivity
import com.example.steganography.R

class EncodeResultFragment : Fragment(R.layout.fragment_encode_result){
    private var title = "Encode: result"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setTitle(title)
    }
}