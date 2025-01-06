package com.srishti.sda

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        // Initialize views
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val googleSignInButton = findViewById<MaterialButton>(R.id.googleSignInButton)
        val emailSignInButton = findViewById<MaterialButton>(R.id.emailSignInButton)
        val resetPasswordButton = findViewById<TextView>(R.id.resetPasswordButton)

        // Set click listeners
        backButton.setOnClickListener {
            finish()
        }

        googleSignInButton.setOnClickListener {
            // TODO: Implement Google Sign-in
            Toast.makeText(this, "Google Sign-in coming soon", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, CategoryPickerActivity::class.java))

        }

        emailSignInButton.setOnClickListener {
            // TODO: Navigate to email login screen
            Toast.makeText(this, "Email login coming soon", Toast.LENGTH_SHORT).show()
        }

        resetPasswordButton.setOnClickListener {
            // TODO: Navigate to password reset screen
            Toast.makeText(this, "Password reset coming soon", Toast.LENGTH_SHORT).show()
        }
    }
} 