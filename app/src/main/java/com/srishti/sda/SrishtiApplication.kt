package com.srishti.sda

import android.app.Application
import com.google.firebase.FirebaseApp

class SrishtiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
} 