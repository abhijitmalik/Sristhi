package com.srishti.sda.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.srishti.sda.R
import com.srishti.sda.databinding.FragmentAddStoryBottomSheetBinding
import com.srishti.sda.model.StoryDataModel
import com.srishti.sda.utils.Constants.Companion.categories
import com.srishti.sda.utils.Helper.Companion.checkInputFields
import com.srishti.sda.utils.Helper.Companion.clearInputFields
import com.srishti.sda.utils.Helper.Companion.getCurrentDate
import com.srishti.sda.utils.Helper.Companion.showPopUp
import com.srishti.sda.viewModel.StoryViewModel

class AddStoryBottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddStoryBottomSheetBinding
    private val viewModel: StoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddStoryBottomSheetBinding.inflate(inflater, container, false)

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


        return binding.root
    }
    private fun submitStory() {
        val sharedPref = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name") as String

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
            viewModel.addStory(binding.categoryDropdown.text.toString(), storyData)
        } else {
            Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
        }
    }
}