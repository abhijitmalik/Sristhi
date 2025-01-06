package com.srishti.sda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Find views
        val appNameText = findViewById<TextView>(R.id.appNameText)
        val animationView = findViewById<LottieAnimationView>(R.id.animationView)

        // Load and start text animation
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        appNameText.startAnimation(fadeIn)

        // Navigate to AuthActivity after animation
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 5000) // 3 seconds delay
    }
} 