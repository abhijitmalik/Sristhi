package com.srishti.sda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: MaterialButton
    private lateinit var forgotPasswordTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        forgotPasswordTextView = view.findViewById(R.id.forgotPasswordButton)

        loginButton.setOnClickListener { loginUser() }
        forgotPasswordTextView.setOnClickListener { resetPassword() }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    saveUserToPreferences(user)
                    Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), UserProfile::class.java))
                } else {
                    Toast.makeText(requireContext(), "Login Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToPreferences(user: FirebaseUser?) {
        user?.let {
            val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("user_name", user.displayName)
            editor.putString("user_email", user.email)
            editor.putString("user_id", user.uid)
            editor.putString("user_photo", user.photoUrl?.toString()) // Store Image URL
            editor.apply()
        }
    }

    private fun resetPassword() {
        val email = emailEditText.text.toString().trim()
        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Enter your email to reset password", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Reset link sent to email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to send reset email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
