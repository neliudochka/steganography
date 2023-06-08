package com.example.steganography.algorithms

import android.R.color


class Pixel(colour: Int) {
    private val numberOfChannels: Int = 3

    private var alpha: Byte
    private var r: Byte
    private var g: Byte
    private var b: Byte

    init {
        alpha = (colour shr 24 and 0xff).toByte()
        r = (colour shr 16 and 0xff).toByte()
        g = (colour shr 8 and 0xff).toByte()
        b = (colour and 0xff).toByte()

    }

    operator fun set(index: Int, value: Byte) {
        require(index in 0 until numberOfChannels)
        require(value.isByte())

        when (index) {
            0 -> r = value
            1 -> g = value
            2 -> b = value
            3 -> alpha = value
        }
    }

    operator fun get(index: Int): Byte {
        require(index in 0 until numberOfChannels)

        when (index) {
            0 -> return r
            1 -> return g
            2 -> return b
            3 -> return alpha
        }

        throw IllegalArgumentException()
    }

    private fun Byte.isByte() = this in Byte.MIN_VALUE..Byte.MAX_VALUE

    fun getColorCode(): Int {
        val hex = String.format("%02x%02x%02x%02x", alpha, r, g, b)
        return hex.toLong(16).toInt()
    }
}