package com.srishti.sda

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



import android.content.Intent

import android.util.Log
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task


public class GoogleSignInActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 100

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in)

        val btnGoogleSignIn = findViewById<com.google.android.gms.common.SignInButton>(R.id.btnGoogleSignIn)
        val tvUserInfo = findViewById<TextView>(R.id.tvUserInfo)

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btnGoogleSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)!!
            onSignInSuccess(account)
        } catch (e: ApiException) {
            onSignInFailure(e)
        }
    }

    private fun onSignInSuccess(account: GoogleSignInAccount) {
        // Show success message
        val tvUserInfo = findViewById<TextView>(R.id.tvUserInfo)
        tvUserInfo.visibility = TextView.VISIBLE
        tvUserInfo.text = "Welcome, ${account.displayName}!"

        Toast.makeText(this, "Sign-In Successful", Toast.LENGTH_SHORT).show()

        // Log user information (optional)
        Log.d("GoogleSignIn", "Name: ${account.displayName}")
        Log.d("GoogleSignIn", "Email: ${account.email}")
        Log.d("GoogleSignIn", "Email: ${account.id}")
        Log.d("GoogleSignIn", "Email: ${account.account}")
        Log.d("GoogleSignIn", "Email: ${account.givenName}")
        Log.d("GoogleSignIn", "Email: ${account.photoUrl}")
        Log.d("GoogleSignIn", "Email: ${account.familyName}")
        Log.d("GoogleSignIn", "Email: ${account.isExpired}")
        Log.d("GoogleSignIn", "Email: ${account.toString()}")

    }

    private fun onSignInFailure(exception: ApiException) {
        // Show error message
        Toast.makeText(this, "Sign-In Failed: ${exception.statusCode}", Toast.LENGTH_SHORT).show()
        Log.e("GoogleSignIn", "Error: ${exception.message}")
    }
}
