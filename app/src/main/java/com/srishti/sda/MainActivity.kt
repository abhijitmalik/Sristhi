package com.srishti.sda

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.srishti.sda.adapter.BookAdapter
import com.srishti.sda.fragment.HomeFragment
import com.srishti.sda.fragment.ProfileFragment
import com.srishti.sda.fragment.StoryUploadFragment
import com.srishti.sda.fragment.UploadFragment
import com.srishti.sda.fragments.CatagoryFragment
import com.srishti.sda.model.Book

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  setupRecyclerViews()


        loadFragment(HomeFragment())
        setupBottomNavigation()

      //  setupBottomNavigation()

    }

   /* private fun setupRecyclerViews() {
        // Sample data - Replace with actual data from your backend
        val topChoiceBooks = listOf(
            Book("1", "Book 1", "Author 1", "url1", "Fiction"),
            Book("2", "Book 2", "Author 2", "url2", "Non-Fiction"),
            // Add more books
        )

        val editorsChoiceBooks = listOf(
            Book("3", "Book 3", "Author 3", "url3", "Mystery"),
            Book("4", "Book 4", "Author 4", "url4", "Romance"),
            // Add more books
        )

        val trendingSeriesBooks = listOf(
            Book("5", "Book 5", "Author 5", "url5", "Fantasy"),
            Book("6", "Book 6", "Author 6", "url6", "Sci-Fi"),
            // Add more books
        )

        findViewById<RecyclerView>(R.id.topChoiceRecyclerView).adapter = 
            BookAdapter(topChoiceBooks) { book ->
                // Handle book click
                Toast.makeText(this, "Selected: ${book.title}", Toast.LENGTH_SHORT).show()
            }

        findViewById<RecyclerView>(R.id.editorsChoiceRecyclerView).adapter = 
            BookAdapter(editorsChoiceBooks) { book ->
                Toast.makeText(this, "Selected: ${book.title}", Toast.LENGTH_SHORT).show()
            }

        findViewById<RecyclerView>(R.id.trendingSeriesRecyclerView).adapter = 
            BookAdapter(trendingSeriesBooks) { book ->
                Toast.makeText(this, "Selected: ${book.title}", Toast.LENGTH_SHORT).show()
            }
    }*/

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_search -> loadFragment(CatagoryFragment())
                R.id.nav_library -> loadFragment(ProfileFragment())
                R.id.nav_profile -> loadFragment(StoryUploadFragment())
            }
            true
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }




    /*private fun setupBottomNavigation() {
        findViewById<BottomNavigationView>(R.id.bottomNavigation).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_search -> {
                    // Handle search
                    true
                }
                R.id.nav_library -> {
                    // Handle library
                    true
                }
                R.id.nav_profile -> {
                    // Handle profile
                    true
                }
                else -> false
            }
        }
    }*/
}