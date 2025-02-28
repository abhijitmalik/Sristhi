package com.srishti.sda.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.srishti.sda.R
import com.srishti.sda.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val ivProfile =view.findViewById<ImageView>(R.id.ivProfile)


        val sharedPref = requireActivity(). getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "No Name")
        val userEmail = sharedPref.getString("user_email", "No Email")
        val userPhotoUrl = sharedPref.getString("user_photo", null)

        // Set Data
        tvName.text = userName
        tvEmail.text = userEmail

        // Load Image from URL using Glide
        if (!userPhotoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(userPhotoUrl)
                .placeholder(R.drawable.ic_google) // Optional Placeholder
                .error(R.drawable.ic_launcher_foreground) // Optional Error Image
                .into(ivProfile)
        } else {
            ivProfile.setImageResource(R.drawable.ic_back) // Default Image
        }
    }

}
