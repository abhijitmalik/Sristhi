package com.srishti.sda.utils

import android.annotation.SuppressLint
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Treading {
    companion object {
        var selcectedCategory: String = "All"
        @SuppressLint("StaticFieldLeak")
        val db: FirebaseFirestore=FirebaseFirestore.getInstance()
        val categories = arrayOf(
            "Children",
            "Biographies",
            "Crime",
            "Business & Business",
            "Erotica",
            "Non-Fiction",
            "Fantasy & SciFi",
            "History",
            "Classics",
            "Poetry",
            "Short Stories",
            "Development",
            "Fiction",
            "Language",
            "Thrillers",
            "Teens & Young Adult",
            "Romance",
            "Religion"
        )

        val auth: FirebaseAuth=FirebaseAuth.getInstance()
    }
}