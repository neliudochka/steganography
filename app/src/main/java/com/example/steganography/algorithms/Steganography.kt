package com.example.steganography.algorithms

import android.graphics.Bitmap

class Steganography {
    fun encode(message: String, bitmap: Bitmap): Bitmap {
        val messageInBytes = CompressString().compress(message)
        val originalImage = Image(bitmap)
        val resultImage = LSB().encode(messageInBytes, originalImage)
        return resultImage.image
    }

    fun decode(bitmap: Bitmap): String {
        val encodedImage = Image(bitmap)
        val compressedMessage = LSB().decode(encodedImage)
        val decodedMessage = CompressString().decompress(compressedMessage)
        return decodedMessage
    //return "mila"
    }
}