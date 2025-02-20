package com.srishti.sda.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.srishti.sda.R
import com.srishti.sda.adapter.StoryListAdapter

class fragment_storylist : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoryListAdapter
    private val db = FirebaseFirestore.getInstance()
    private val storiesList = mutableListOf<Story>()
    private var selectedCategory: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_storylist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedCategory = arguments?.getString("category")
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StoryListAdapter()
        recyclerView.adapter = adapter

        fetchBiographiesStories(selectedCategory)
    }

    private fun fetchBiographiesStories(selectedCategory: String?) {
        db.collection("stories")
            .whereEqualTo("category", selectedCategory)
            .get()
            .addOnSuccessListener { documents ->
                storiesList.clear()
                for (document in documents) {
                    val story = Story(
                        author = document.getString("author") ?: "Unknown",
                        likes = document.getLong("likes")?.toInt() ?: 0,
                        story = document.getString("story") ?: "No story available",
                        ImageURL = document.getString("ImageURL") ?: "No Image available",//"https://res.cloudinary.com/dwbnwxau1/image/upload/android_uploads/wpb9wjvqbybqw9o7otou.jpg",

                    )
                    Log.e("Story", "Author: ${story.author}, Likes: ${story.likes}, Story: ${story.story}")
                    storiesList.add(story)
                }
                adapter.updateStories(storiesList)
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

data class Story(
    val author: String,
    val likes: Int,
    val story: String,
    val ImageURL: String,
)
