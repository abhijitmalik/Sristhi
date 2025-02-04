package com.srishti.sda

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth



class AuthActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        val loginButton = findViewById<TextView>(R.id.loginButton)
        val googleSignInButton = findViewById<MaterialButton>(R.id.googleSignInButton)
        val emailSignInButton = findViewById<MaterialButton>(R.id.emailSignInButton)
        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        auth = FirebaseAuth.getInstance()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        // Initialize views


        // Set click listeners
        backButton.setOnClickListener {
            finish()
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        googleSignInButton.setOnClickListener {
            // TODO: Implement Google Sign-in
            signIn()

        }

        emailSignInButton.setOnClickListener {
            startActivity(Intent(this, EmailRegistrationActivity::class.java))
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
        //val tvUserInfo = findViewById<TextView>(R.id.tvUserInfo)
     //   tvUserInfo.visibility = TextView.VISIBLE
      //  tvUserInfo.text = "Welcome, ${account.displayName}!"

        Toast.makeText(this, "Sign-In Successful", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Name: ${account.displayName}",Toast.LENGTH_SHORT).show()



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



        // Store user details in SharedPreferences
        val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("user_name", account.displayName)
        editor.putString("user_email", account.email)
        editor.putString("user_id", account.id)
        editor.putString("user_photo", account.photoUrl?.toString()) // Store Image URL
        editor.apply()

        // Navigate to Next Activity
     val Intent = Intent(this, UserProfile::class.java)
        startActivity(Intent)

    }

    private fun onSignInFailure(exception: ApiException) {
        // Show error message
        Toast.makeText(this, "Sign-In Failed: ${exception.statusCode}", Toast.LENGTH_SHORT).show()
        Log.e("GoogleSignIn", "Error: ${exception.message}")
    }
} 