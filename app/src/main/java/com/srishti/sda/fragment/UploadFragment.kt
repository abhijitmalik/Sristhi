package com.srishti.sda.fragment


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.srishti.sda.R
import com.srishti.sda.adapter.CategoryAdapter
import com.srishti.sda.adapter.StoryCategoryAdapter
import com.srishti.sda.databinding.FragmentUploadBinding
import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.utils.Constants.Companion.categories
import com.srishti.sda.utils.Helper.Companion.checkInputFields
import com.srishti.sda.utils.Helper.Companion.clearInputFields
import com.srishti.sda.utils.Helper.Companion.getCurrentDate
import com.srishti.sda.utils.Helper.Companion.showPopUp
import com.srishti.sda.viewModel.StoryViewModel

class UploadFragment : Fragment() {
    //updated
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


        binding.rvCategoryStory.layoutManager = LinearLayoutManager(requireContext())

        // Observe ViewModel Data
        viewModel.categoriesWithStories.observe(viewLifecycleOwner) { categories ->
            if (categories.isNotEmpty()) {
                binding.rvCategoryStory.adapter = StoryCategoryAdapter(categories, requireContext())
            } else {
                Toast.makeText(context, "Unable to show details", Toast.LENGTH_SHORT).show()

            }
        }

        // Observe Errors
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            Log.d("Error", errorMessage)
            Toast.makeText(context, "Unable to show details", Toast.LENGTH_SHORT).show()

        }

        // Fetch data from Firestore
        viewModel.fetchAllCategoriesWithStories()

        binding.btnAdd.setOnClickListener {
            val bottomSheet = AddStoryBottomSheetFragment()
            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        }

    }


}
