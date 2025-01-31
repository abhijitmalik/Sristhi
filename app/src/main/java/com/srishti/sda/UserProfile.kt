package com.srishti.sda

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val tvName = findViewById<TextView>(R.id.tvName)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val ivProfile = findViewById<ImageView>(R.id.ivProfile)

        // Retrieve data from SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
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