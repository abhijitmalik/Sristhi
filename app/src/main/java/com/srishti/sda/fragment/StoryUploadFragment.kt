package com.srishti.sda.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.srishti.sda.R
import com.srishti.sda.cloudinary.CloudinaryHelper
import com.srishti.sda.databinding.FragmentStoryUploadBinding
import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.utils.Constants.Companion.categories
import com.srishti.sda.utils.Helper.Companion.checkInputFields
import com.srishti.sda.utils.Helper.Companion.clearInputFields
import com.srishti.sda.utils.Helper.Companion.getCurrentDate
import com.srishti.sda.utils.Helper.Companion.showPopUp
import com.srishti.sda.viewModel.StoryViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoryUploadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoryUploadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentStoryUploadBinding
    private val viewModel: StoryViewModel by viewModels()
    private val btn_imageView: ImageView? = null
    private val imageIdEditText: EditText? = null
    private val pickImageButton: Button? = null
    private var uploadImageButton: android.widget.Button? = null
    private var loadImageButton: android.widget.Button? = null
    private var selectedImageUri: Uri? = null
    private var uploadedImageId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStoryUploadBinding.inflate(inflater, container, false)
        Toast.makeText(context, "Upload Fragment", Toast.LENGTH_SHORT).show()


        //  return inflater.inflate(R.layout.fragment_story_upload, container, false)
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name")
        val userEmail = sharedPref.getString("user_email", "No Email")
        binding.emailInput.setText(userEmail)
        // Initialize Cloudinary
        try {

            CloudinaryHelper.init(context)
        }catch (e:Exception){
            Log.d("Error",e.message.toString())
        }

        binding.pickImageButton.setOnClickListener() {
            openGallery()
        }
        // Observe Upload Status
        viewModel.uploadStatus.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                context?.let {
                    it.getDrawable(R.drawable.ic_success)?.let { it1 ->
                        showPopUp(
                            context = it,
                            icon = it1,
                            animation = 0,
                            messsage = "Story uploaded successfully!",
                            intentActivity = null
                        )
                    }
                }

                clearInputFields(
                    arrayOf(
                        binding.categoryDropdown,
                        binding.storyInput
                    )
                )

            }.onFailure {
                val msg: String = it.message.toString()
                context?.let {
                    it.getDrawable(R.drawable.ic_fail)?.let { it1 ->
                        showPopUp(
                            context = it,
                            icon = it1,
                            animation = 0,
                            messsage = "Failed: ${msg}",
                            intentActivity = null
                        )
                    }
                }
            }
            binding.submitButton.isEnabled = true
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        binding.categoryDropdown.setAdapter(adapter)

        binding.submitButton.setOnClickListener {
            if (checkInputFields(
                    arrayOf(
                        binding.emailInput,
                        binding.categoryDropdown,
                        binding.storyInput
                    )
                )
            ) {
                //  submitStory()
                UploadImageToClodnary();
            }
        }
        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }


    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                selectedImageUri = result.data?.data
                context?.let { Glide.with(it).load(selectedImageUri).into(binding.imagePreView) }
            }
        }


    private fun UploadImageToClodnary() {

        if (binding.imagePreView.drawable != null) {
            // Get Bitmap from ImageView
            val bitmap = (binding.imagePreView.drawable as BitmapDrawable).bitmap

            // Convert Bitmap to File
            val imageFile = convertBitmapToFile(bitmap)

            if (imageFile != null) {
                // Upload image to Cloudinary
                CloudinaryHelper.uploadImage(
                    context,
                    Uri.fromFile(imageFile),
                    object : CloudinaryHelper.CloudinaryUploadCallback {
                        override fun onSuccess(publicId: String, imageUrl: String) {
                            uploadedImageId = publicId
                            requireActivity().runOnUiThread {
                                Toast.makeText(context, "Image Upload Success!", Toast.LENGTH_SHORT)
                                    .show()
                                submitStory()


                            }

                        }

                        override fun onFailure(errorMessage: String) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(context, "Upload Failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            } else {
                Toast.makeText(context, "Error converting image.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "No Image Selected!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun convertBitmapToFile(bitmap: Bitmap): File? {
        return try {
            val file = File.createTempFile(
                "image_",
                ".jpg",
                context?.cacheDir ?: return null
            ) // Directly use cacheDir
            FileOutputStream(file).use { fos ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos) // Compress Bitmap to JPEG
                fos.flush()
            }
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    private fun submitStory() {
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name") as String
        val imageUrl: String? =
            "https://res.cloudinary.com/dwbnwxau1/image/upload/" + uploadedImageId + ".jpg" //dwbnwxau1-->base name

        val storyData =
            imageUrl?.let {
                StoryDataModel(
                    email = binding.emailInput.text.toString(),
                    story = binding.storyInput.text.toString(),
                    posted = getCurrentDate(),
                    likes = 0.toString(),
                    id = id.toString(),
                    author = userName,
                    ImageUrl = it,

                    )
            }

        binding.submitButton.isEnabled = false

        if (storyData != null) {
            viewModel.addStory(binding.categoryDropdown.text.toString(), storyData)
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StoryUploadFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}