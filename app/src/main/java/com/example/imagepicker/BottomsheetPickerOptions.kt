package com.example.imagepicker

import android.R
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.opengl.ETC1.encodeImage
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.imagepicker.databinding.PickerOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class BottomsheetPickerOptions(private var listener: PickerOptions) : BottomSheetDialogFragment() {
     private var binding: PickerOptionsBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PickerOptionsBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    enum class option {
        CAMERA, GALLERY
    }

    private fun init() {
        binding?.ivCamera?.setOnClickListener {
            listener.optionSelected(option.CAMERA)
         }

        binding?.ivGallery?.setOnClickListener {
            listener.optionSelected(option.GALLERY)
        }
    }



    interface PickerOptions {
        fun optionSelected(selectedOption: option)
    }
}