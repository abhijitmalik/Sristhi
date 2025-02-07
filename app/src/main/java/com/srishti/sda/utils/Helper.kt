package com.srishti.sda.utils

import android.icu.text.SimpleDateFormat
import java.security.SecureRandom
import java.util.Date
import java.util.Locale

class Helper {
    companion object {

        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return sdf.format(Date())
        }

        fun generateRandomId(): String {
            val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
            val random = SecureRandom()
            return (1..6)
                .map { chars[random.nextInt(chars.length)] }
                .joinToString("")
        }
    }
}