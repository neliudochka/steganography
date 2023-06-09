package com.example.steganography.fragments.encode

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.steganography.MainActivity
import com.example.steganography.R
import com.example.steganography.algorithms.Steganography
import com.example.steganography.databinding.FragmentEncodeResultBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class EncodeResultFragment : Fragment(R.layout.fragment_encode_result){
    private lateinit var binding: FragmentEncodeResultBinding
    private var title = "Encode: result"

    private val args : EncodeResultFragmentArgs by navArgs()
    private val previewImage by lazy { binding.image }

    lateinit var message: String
    lateinit var imageUri: Uri

    lateinit var resultBitmap: Bitmap
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEncodeResultBinding.bind(view)
        (activity as MainActivity).supportActionBar?.setTitle(title)

        setArgs()
        startEncode()
        initButtons(view)
    }

    private fun startEncode() {
        val bitmap = BitmapFactory.decodeStream(
            requireContext().contentResolver.openInputStream(imageUri))
        val finalImage = Steganography().encode(message, bitmap)
        resultBitmap = finalImage
        binding.image2.setImageBitmap(finalImage)
    }

    private fun setArgs() {
        // Receive the arguments in a variable
        message = this.args.message
        imageUri = Uri.parse(this.args.imageUri)
        // set the values to respective textViews
        binding.text.text = message
        previewImage.setImageURI(imageUri)
    }

    private fun initButtons(view: View) {
        initToSetup(view)
        initSavePicture()
    }

    private fun initToSetup(view: View) {
        binding.toSetup.setOnClickListener {
            view.findNavController().navigate(R.id.action_encodeResultFragment_to_encodeSetupFragment)
        }
    }

    private fun initSavePicture() {
        binding.buttonSavePicture.setOnClickListener{
            //save pick
            val bitmap = BitmapFactory.decodeStream(
                requireContext().contentResolver.openInputStream(imageUri))
            if(resultBitmap == bitmap) {
                Toast.makeText(context, "shitshotshit...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "NOTshitshotshit...", Toast.LENGTH_SHORT).show()
            }
            saveBitmapImage(resultBitmap)
        }
    }


    private fun saveBitmapImage(bitmap: Bitmap) {
        val contentResolver = activity?.contentResolver
        val timestamp = System.currentTimeMillis()

        //Tell the media scanner about the new file so that it is immediately available to the user.
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, timestamp)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, timestamp)
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name))
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            val uri = contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                try {
                    val outputStream = contentResolver.openOutputStream(uri)
                    if (outputStream != null) {

                        try {
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                            outputStream.close()
                        } catch (e: Exception) {
                            Log.e(TAG, "saveBitmapImage: ", e)
                        }

                    }
                    values.put(MediaStore.Images.Media.IS_PENDING, false)
                    contentResolver.update(uri, values, null, null)

                    Toast.makeText(context, "Saved...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e(TAG, "saveBitmapImage: ", e)
                }
            }
        } else {
            val imageFileFolder = File(Environment.getExternalStorageDirectory().toString() + '/' + getString(R.string.app_name))
            if (!imageFileFolder.exists()) {
                imageFileFolder.mkdirs()
            }
            val mImageName = "$timestamp.png"
            val imageFile = File(imageFileFolder, mImageName)
            try {
                val outputStream: OutputStream = FileOutputStream(imageFile)
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.close()
                } catch (e: Exception) {
                    Log.e(TAG, "saveBitmapImage: ", e)
                }
                values.put(MediaStore.Images.Media.DATA, imageFile.absolutePath)
                if (contentResolver != null) {
                    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                }

                Toast.makeText(context, "Saved...", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e(TAG, "saveBitmapImage: ", e)
            }
        }
    }
}