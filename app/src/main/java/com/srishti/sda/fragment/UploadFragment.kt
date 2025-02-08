package com.srishti.sda.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.srishti.sda.R
import com.srishti.sda.databinding.FragmentUploadBinding
import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.utils.Constants.Companion.categories
import com.srishti.sda.utils.Helper.Companion.checkInputFields
import com.srishti.sda.utils.Helper.Companion.clearInputFields
import com.srishti.sda.utils.Helper.Companion.getCurrentDate
import com.srishti.sda.utils.Helper.Companion.showPopUp
import com.srishti.sda.viewModel.StoryViewModel

class UploadFragment : Fragment() {
    //update modify
//    private lateinit var emailInput: TextInputEditText
//    private lateinit var categoryDropdown: AutoCompleteTextView
//    private lateinit var storyInput: TextInputEditText
//    private lateinit var submitButton: MaterialButton
//    private lateinit var feedbackText: TextView
    private lateinit var binding: FragmentUploadBinding
    private val viewModel: StoryViewModel by viewModels()

    // Setup category dropdown

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name")
        val userEmail = sharedPref.getString("user_email", "No Email")
        binding.emailInput.setText(userEmail)


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

//        emailInput = view.findViewById(R.id.emailInput)
//        emailInput.setText(userEmail)
//        categoryDropdown = view.findViewById(R.id.categoryDropdown)
//        storyInput = view.findViewById(R.id.storyInput)
//        submitButton = view.findViewById(R.id.submitButton)
//        feedbackText = view.findViewById(R.id.feedbackText)

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
                submitStory()
            }
        }
    }

    private fun submitStory() {
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name") as String
//        val email = emailInput.text.toString()
//        val category = categoryDropdown.text.toString()
//        val story = storyInput.text.toString()
//        val date = Helper.getCurrentDate()
//        val id = Helper.generateRandomId()
//        val author = userName
//
//        if (email.isEmpty() || category.isEmpty() || story.isEmpty()) {
//            showFeedback("Please fill in all fields", false)
//            return
//        }
//
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            showFeedback("Please enter a valid email address", false)
//            return
//        }

//        val storyData = hashMapOf(
//            "email" to email,
//            "category" to category,
//            "story" to story,
//            "posted" to  date,
//            "likes" to 0,
//            "id" to id,
//            "author" to author
//        )

        val storyData =
            userName.let {
                StoryDataModel(
                    email = binding.emailInput.text.toString(),
                    story = binding.storyInput.text.toString(),
                    posted = getCurrentDate(),
                    likes = 0.toString(),
                    id = id.toString(),
                    author = it
                )
            }

        binding.submitButton.isEnabled = false

        if (storyData != null) {
//            db.collection("stories")
//                .add(storyData)
//                .addOnSuccessListener {
//                    showFeedback("Story submitted successfully!", true)
//                    clearInputs()
//                }
//                .addOnFailureListener { e ->
//                    showFeedback("Error: ${e.message}", false)
//                    binding.submitButton.isEnabled = true
//                }
            viewModel.addStory(binding.categoryDropdown.text.toString(), storyData)
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun showFeedback(message: String, isSuccess: Boolean) {
//        binding.feedbackText.apply {
//            text = message
//            setTextColor(
//                ContextCompat.getColor(
//                    requireContext(),
//                    if (isSuccess) android.R.color.holo_green_dark
//                    else android.R.color.holo_red_dark
//                )
//            )
//            visibility = View.VISIBLE
//        }
//    }
//
//    private fun clearInputs() {
//        binding.emailInput.text?.clear()
//        binding.categoryDropdown.text?.clear()
//        binding.storyInput.text?.clear()
//        binding.submitButton.isEnabled = true
//    }
}
