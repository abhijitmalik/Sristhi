package com.srishti.sda.fragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.srishti.sda.R
import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.utils.Constants
import com.srishti.sda.utils.Helper
import java.security.SecureRandom
import java.util.Date
import java.util.Locale

class UploadFragment : Fragment() {
    //update
    private lateinit var emailInput: TextInputEditText
    private lateinit var categoryDropdown: AutoCompleteTextView
    private lateinit var storyInput: TextInputEditText
    private lateinit var submitButton: MaterialButton
    private lateinit var feedbackText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_upload, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views

        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name")
        val userEmail = sharedPref.getString("user_email", "No Email")

        emailInput = view.findViewById(R.id.emailInput)
        emailInput.setText(userEmail)
        categoryDropdown = view.findViewById(R.id.categoryDropdown)
        storyInput = view.findViewById(R.id.storyInput)
        submitButton = view.findViewById(R.id.submitButton)
        feedbackText = view.findViewById(R.id.feedbackText)

        // Setup category dropdown
        val categories = arrayOf(
            "Children",
            "Biographies",
            "Crime",
            "Business & Business",
            "Erotica",
            "Non-Fiction",
            "Fantasy & SciFi",
            "History",
            "Classics",
            "Poetry",
            "Short Stories",
            "Development",
            "Fiction",
            "Language",
            "Thrillers",
            "Teens & Young Adult",
            "Romance",
            "Religion"
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, categories)
        categoryDropdown.setAdapter(adapter)

        submitButton.setOnClickListener {
            submitStory()
        }
    }

    private fun submitStory() {
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name")
        val email = emailInput.text.toString()
        val category = categoryDropdown.text.toString()
        val story = storyInput.text.toString()
        val date = Helper.getCurrentDate()
        val id = Helper.generateRandomId()
        val author = userName

        if (email.isEmpty() || category.isEmpty() || story.isEmpty()) {
            showFeedback("Please fill in all fields", false)
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showFeedback("Please enter a valid email address", false)
            return
        }

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
            author?.let { StoryDataModel(email, category, story, date, 0.toString(), id, it) }

        submitButton.isEnabled = false

        if (storyData != null) {
            Constants.db.collection("stories")
                .add(storyData)
                .addOnSuccessListener {
                    showFeedback("Story submitted successfully!", true)
                    clearInputs()
                }
                .addOnFailureListener { e ->
                    showFeedback("Error: ${e.message}", false)
                    submitButton.isEnabled = true
                }
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showFeedback(message: String, isSuccess: Boolean) {
        feedbackText.apply {
            text = message
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (isSuccess) android.R.color.holo_green_dark
                    else android.R.color.holo_red_dark
                )
            )
            visibility = View.VISIBLE
        }
    }

    private fun clearInputs() {
        emailInput.text?.clear()
        categoryDropdown.text?.clear()
        storyInput.text?.clear()
        submitButton.isEnabled = true
    }
}
