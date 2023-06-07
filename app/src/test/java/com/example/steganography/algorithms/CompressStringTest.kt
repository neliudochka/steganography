package com.example.steganography

import com.example.steganography.algorithms.CompressString
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CompressStringTest {
    private val compressTestData = arrayOf(
        "maybe in the next life",
        "I'll rewrite them, when I have enough time",
        "yes!",
        "one two 3, SmilE)00)",
        "",
        "testsTests"
    )

    @Test
    fun compressedDecompressedCompressed2() {
        compressTestData.forEach {
            val input = it
            val byteArr = CompressString().compress(input)
            val sameInput = CompressString().decompress(byteArr)
            assertEquals(input, sameInput)
        }
    }
}