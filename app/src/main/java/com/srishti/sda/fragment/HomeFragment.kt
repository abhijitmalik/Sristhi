package com.srishti.sda.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.srishti.sda.R
import com.srishti.sda.adapter.CategoryAdapter
import com.srishti.sda.databinding.FragmentHomeBinding
import com.srishti.sda.databinding.TopBarLayBinding

public class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var topBarLayBinding: TopBarLayBinding
    lateinit var shrdPref: SharedPreferences
    private lateinit var draggableLayout: View
    private var initialY = 0f
    private var dY = 0f
    private val threshold = 300 // Distance to hide the layout

    val categories = listOf(
        "All",
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        topBarLayBinding = TopBarLayBinding.bind(binding.topBar.root)

        shrdPref = requireActivity().getSharedPreferences("UserPrefs", Activity.MODE_PRIVATE)
        val userPhotoUrl = shrdPref.getString("user_photo", null)
        if (!userPhotoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(userPhotoUrl)
                .placeholder(R.drawable.ic_google) // Optional Placeholder
                .error(R.drawable.ic_launcher_foreground) // Optional Error Image
                .into(topBarLayBinding.userLogo)
        } else {
            topBarLayBinding.userLogo.setImageResource(R.drawable.ic_back) // Default Image
        }
        val adapter = CategoryAdapter(categories, requireContext())
        binding.rvCategory.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvCategory.adapter = adapter
        draggableLayout = binding.laySearch.searchCard
        draggableLayout.visibility = View.GONE
        draggableLayout.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialY = v.y
                    dY = event.rawY - v.y
                }

                MotionEvent.ACTION_MOVE -> {
                    v.y = event.rawY - dY // Move up/down based on touch
                }

                MotionEvent.ACTION_UP -> {
                    if (initialY - v.y > threshold) { // Check if dragged UP
                        v.animate()
                            .translationY(-v.height.toFloat()) // Move up & hide
                            .alpha(0f)
                            .setDuration(300)
                            .withEndAction {
                                v.visibility = View.GONE
                                topBarLayBinding.searchLay.visibility = View.VISIBLE // Show button
                            }
                            .start()
                    } else {
                        v.animate().translationY(0f).setDuration(300).start() // Snap back
                    }
                }
            }
            true
        }
        topBarLayBinding.searchLay.setOnClickListener {
            draggableLayout.visibility = View.VISIBLE
            draggableLayout.animate()
                .translationY(0f)
                .alpha(1f)
                .setDuration(300)
                .start()
            topBarLayBinding.searchLay.visibility = View.GONE // Hide button
        }


        return binding.root
    }
}
