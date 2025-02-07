package com.srishti.sda.utils

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

class Constants {
    companion object {
        var selcectedCategory: String = "All"
        @SuppressLint("StaticFieldLeak")
        val db: FirebaseFirestore=FirebaseFirestore.getInstance()
    }
}