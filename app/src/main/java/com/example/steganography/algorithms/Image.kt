package com.example.steganography.algorithms

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap

class Image(bitmap: Bitmap) {
    val image: Bitmap = bitmap
    val height: Int = image.height
    val width: Int = image.width


    operator fun get(i: Int, j: Int): Pixel {
        require(i in 0 until width && j in 0 until height)
        val colour: Int = image.getPixel(i, j)
        return Pixel(colour)
    }

    operator fun set(i: Int, j: Int, p: Pixel) {
        //require(i in 0 until width && j in 0 until height)
        image.setPixel(i, j, p.getColorCode())
        //image.setPixel(i, j, Color.RED)
        //image.setPixel(i, j, Color.BLACK)
    }

    fun copy(): Image {
        val bm = image.copy(image.config, true)
        return Image(bm)
    }


}
