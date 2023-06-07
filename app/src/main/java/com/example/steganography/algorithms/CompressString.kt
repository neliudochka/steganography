package com.example.steganography.algorithms

import java.io.ByteArrayOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class CompressString {
    fun compress(input: String) :ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter(Charsets.UTF_8).use { it.write(input) }
        return bos.toByteArray()
    }

    fun decompress(input :ByteArray) :String {
        return GZIPInputStream(input.inputStream())
            .bufferedReader(Charsets.UTF_8).use { it.readText() }
    }
}