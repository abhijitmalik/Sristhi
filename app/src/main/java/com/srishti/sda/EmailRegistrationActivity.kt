package com.srishti.sda

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EmailRegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_registration)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        //github push

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val usernameInput = findViewById<TextInputEditText>(R.id.usernameInput)
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val ageInput = findViewById<TextInputEditText>(R.id.ageInput)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)

        backButton.setOnClickListener {
            finish()
        }

        registerButton.setOnClickListener {
            val username = usernameInput.text?.toString()?.trim()
            val email = emailInput.text?.toString()?.trim()
            val password = passwordInput.text?.toString()?.trim()
            val age = ageInput.text?.toString()?.trim()

            if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(username, email, password, age)
        }
    }

    private fun registerUser(username: String, email: String, password: String, age: String?) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Store additional user data in Firestore
                    val user = hashMapOf(
                        "username" to username,
                        "email" to email
                    )
                    
                    if (!age.isNullOrEmpty()) {
                        user["age"] = age.toInt().toString()
                    }

                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        db.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, CategoryPickerActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
} 