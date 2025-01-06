package com.srishti.sda

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()

        // Initialize views
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val loginButton = findViewById<TextView>(R.id.loginButton)
        val googleSignInButton = findViewById<MaterialButton>(R.id.googleSignInButton)
        val emailSignInButton = findViewById<MaterialButton>(R.id.emailSignInButton)

        // Set click listeners
        backButton.setOnClickListener {
            finish()
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        googleSignInButton.setOnClickListener {
            // TODO: Implement Google Sign-in
        }

        emailSignInButton.setOnClickListener {
            startActivity(Intent(this, EmailRegistrationActivity::class.java))
        }
    }
} 