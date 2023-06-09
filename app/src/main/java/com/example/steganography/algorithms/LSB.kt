package com.example.steganography.algorithms

import android.util.Log
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.experimental.or

class LSB {
    //number for least least-significant bits to use
    //for encoding the message
    private val numberOfBits: Int = 1
    private val numberOfChannels: Int = 4
    private val bitsInByte: Int = 8

    fun encode(message: ByteArray, coverImage: Image): Image {
        // Check that message fits into image
        val numberOfBytesInImage = coverImage.width * coverImage.height * numberOfChannels
        val numberOfUsableBitsInImage = numberOfBytesInImage * this.numberOfBits
        if (numberOfUsableBitsInImage < message.size * bitsInByte + numberOfChannels)
            throw RuntimeException("Message is too big to fit in the cover image")
        val buffer = ByteBuffer.allocate(numberOfChannels)
        buffer.putInt(message.size)
        val sizeByteArray = buffer.array()
        val newMessage = sizeByteArray + message

        return fill(newMessage, coverImage)
    }

    fun decode(coverImage: Image): ByteArray {
        val im = coverImage.copy()
        val lsb = getLSB(coverImage[0, 0])
        val messageSize = ByteBuffer.wrap(readKByte(lsb, 0, 4, im)).int
        val message = readKByte(lsb, 4 * 8, messageSize, im)
        //Log.d("mes", String(message))
        //return message
        return CompressString().compress("milasha")
    }

    // Function that actually writes the message to the cover image
    private fun fill(message: ByteArray, coverImage: Image): Image {
        val result = coverImage.copy()

        val lsbPixel = result[0, 0]
        var lsb = numberOfBits - 1

        for (channel in 0 until 3) {
            Log.d("smth", lsbPixel[channel].toInt().toString())
            lsbPixel[channel] = lsbPixel[channel] and 0xFE.toByte() or (lsb and 1).toByte()
            lsb = lsb shr 1
        }
        result[0, 0] = lsbPixel

        val k = numberOfBits
        var offset = 0
        var hasFinished = false
        for (i in 0 until result.width) {
            if (hasFinished) break
            for (j in 0 until result.height) {
                if (i == 0 && j == 0) continue
                if (hasFinished) break
                val pixel = result[i, j]
                for (channel in 0 until numberOfChannels) {
                    val chunk = getKBits(message, k, offset)
                    pixel[channel] = (pixel[channel] and (0xFF shl k).toByte() or chunk.toByte())
                    offset += k
                    if (offset >= message.size * bitsInByte) {
                        hasFinished = true
                        break
                    }
                }
                result[i, j] = pixel
            }
        }

        return result
    }

    private fun getKBits(message: ByteArray, k: Int, offset: Int): Int {
        require(k in 0..8 && offset in 0..message.size * 8)

        val byteIndex = offset / 8
        val bitIndex = offset % 8

        if (byteIndex < message.size - 1) {
            val number = (message[byteIndex].toInt().shl(8)) or (message[byteIndex + 1].toInt() and 255)

            val thing1 = number shr (16 - (bitIndex + k))
            val thing2 = (1 shl k) - 1
            return (thing1) and (thing2)

        } else {
            val number = (message[byteIndex].toInt())
            val shiftRight = maxOf((8 - (bitIndex + k)), 0)
            val shiftLeft = minOf(8 - bitIndex, k)
            val res = (number shr shiftRight) and ((1 shl shiftLeft) - 1)
            if ((8 - bitIndex) < k)
                return res shl (k - (8 - bitIndex))
            return res
        }
    }

    fun getLSB(pixel: Pixel): Int {
        var result = 0
        for (i in 0 until numberOfChannels) {
            result = result + (pixel[i] and 1) * (Math.pow(2.0, i.toDouble())).toInt()
        }
        return result + 1
    }

    fun readKByte(lsb: Int, offset: Int, k: Int, image: Image): ByteArray {
        var counter = 0
        val numberOfBits = k * 8
        var seekPointer = 0
        var hasReachedOffset = false
        var hasFinished = false
        val byteBuffer = ByteBuffer.allocate(k)
        var byte: Byte = 0

        for (i in 0 until image.width) {
            if (hasFinished) break
            for (j in 0 until image.height) {
                if (hasFinished) break
                if (i == 0 && j == 0) continue
                if (!hasReachedOffset) {
                    if (seekPointer == offset) {
                        hasReachedOffset = true
                    }
                }
                for (channel in 0 until numberOfChannels) {
                    if (hasFinished) break
                    val data = image[i, j][channel]
                    for (bit in lsb - 1 downTo 0) {
                        if (hasFinished) break
                        if (!hasReachedOffset) {
                            seekPointer++
                            if (seekPointer == offset) {
                                hasReachedOffset = true
                            }
                        } else {
                            byte = Byte_set(byte, counter++ % 8, data[bit])
                            if (counter % 8 == 0 && counter > 0) {
                                byteBuffer.put(byte)
                                byte = 0
                            }
                            hasFinished = counter == numberOfBits
                        }
                    }
                }
            }
        }
        return byteBuffer.array()
    }

    operator fun Byte.get(position: Int): Boolean {
        return (this.toInt() shr position and 1) == 1
    }

    private fun Byte_set(byte: Byte, position: Int, value: Boolean): Byte {
        if (value)
            return (byte or ((1 shl (7 - position)).toByte()))
        else
            return byte and (1 shl (7 - position)).inv().toByte()

    }

}

