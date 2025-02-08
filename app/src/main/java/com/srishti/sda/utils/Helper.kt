package com.srishti.sda.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import com.srishti.sda.R
import com.srishti.sda.databinding.PopUpLayBinding
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

        fun checkInputFields(edFields: Array<EditText>): Boolean {
            for (field in edFields) {
                if (field.text.toString().isEmpty()) {
                    field.error = "Required"
                    return false
                }

            }
            return true
        }

        fun clearInputFields(edFields: Array<EditText>) {
            for (field in edFields) {
                field.text.clear()
            }
        }

        fun showPopUp(
            context: Context,
            icon: Drawable,
            animation: Int,
            messsage: String,
            intentActivity: Activity?
        ) {
            val dialog = Dialog(context)
            val binding = PopUpLayBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(binding.root)
            dialog.window?.setLayout(900, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window?.setGravity(Gravity.CENTER)
            dialog.setCancelable(false)
            if (icon != null) {
                binding.imgStatus.setImageDrawable(icon)
                binding.imgStatus.visibility = View.VISIBLE
                binding.animationView.visibility = View.GONE
            } else if (animation != 0) {
                binding.animationView.setAnimation(animation)
                binding.imgStatus.visibility = View.GONE
                binding.animationView.visibility = View.VISIBLE
            }
            binding.tvPopup.text = messsage
            binding.btnOk.setOnClickListener {
                if (intentActivity != null) {
                    val intent = Intent(context, intentActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    (context as? Activity)?.finish()
                }
                dialog.dismiss()
            }


            dialog.show()
        }
    }
}