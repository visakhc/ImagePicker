package com.example.imagepicker

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import coil.load
import           com.example.imagepicker.BottomsheetPickerOptions.option

import com.example.imagepicker.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity(), BottomsheetPickerOptions.PickerOptions {
    private var binding: ActivityMainBinding? = null
    private lateinit var mImageUri: Uri/*? = null*/
    private lateinit var bottomsheetPickerOptions: BottomsheetPickerOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        bottomsheetPickerOptions = BottomsheetPickerOptions(this)
        binding?.button?.setOnClickListener {
            bottomsheetPickerOptions.show(supportFragmentManager, "picker")
        }
    }


    override fun optionSelected(selectedOption: option) {
        when (selectedOption) {
            option.CAMERA -> {
                openCamera()
            }
            option.GALLERY -> {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
        galleryIntent.type = "image/*"

        startActivityForResult(Intent.createChooser(galleryIntent, "Pick Image"), 1)
    }

    private fun openCamera() {

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "HiLITE_invoice.jpg")
        values.put(
            MediaStore.Images.Media.DESCRIPTION,
            "Photo taken on " + System.currentTimeMillis()
        )
        contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?.let {
                mImageUri = it
            }
        val outFile = mImageUri.path?.let {
            File(it)
        }
        val mCameraFileName = outFile.toString()
        printLog("outFile   $outFile \n  ")
        if (outFile != null) {
            printLog("outFileName   ${outFile.name} \n  ")
        }
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)

        startActivityForResult(cameraIntent, 0)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0 -> if (resultCode == RESULT_OK) {
                bottomsheetPickerOptions.dismiss()
                binding?.ivResult?.load(mImageUri)
                printLog("mImageUri   $mImageUri  ")

            }
            1 -> if (resultCode == RESULT_OK) {
                bottomsheetPickerOptions.dismiss()
                binding?.ivResult?.load(data?.data)
                printLog("data?.data   ${data?.data}  ")

            }
        }
    }

    private fun printLog(text: String) {
        binding?.tvLog?.append(text)
    }
}