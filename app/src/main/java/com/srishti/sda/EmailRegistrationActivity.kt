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

//update
class EmailRegistrationActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_registration)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val usernameInput = findViewById<TextInputEditText>(R.id.usernameInput)
        val emailInput = findViewById<TextInputEditText>(R.id.emailInput)
        val passwordInput = findViewById<TextInputEditText>(R.id.passwordInput)
        val ageInput = findViewById<TextInputEditText>(R.id.ageInput)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameInput.text?.toString()?.trim()
            val email = emailInput.text?.toString()?.trim()
            val password = passwordInput.text?.toString()?.trim()
            val age = ageInput.text?.toString()?.trim()

            if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
                showToast("Please fill in all required fields.")
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Invalid email format.")
                return@setOnClickListener
            }

            if (password.length < 6) {
                showToast("Password must be at least 6 characters.")
                return@setOnClickListener
            }

            registerUser(username, email, password, age)
        }
    }

    private fun registerUser(username: String, email: String, password: String, age: String?) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        val user = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "age" to (age ?: "Not Provided")
                        )

                        db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                showToast("Registration successful!")
                               /* startActivity(Intent(this, CategoryPickerActivity::class.java))
                                finish()*/

                            }
                            .addOnFailureListener { e ->
                                showToast("Error saving user: ${e.message}")
                            }
                    }
                } else {
                    showToast("Registration failed: ${task.exception?.message}")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
