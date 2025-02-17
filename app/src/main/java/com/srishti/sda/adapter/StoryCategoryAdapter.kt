package com.srishti.sda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srishti.sda.databinding.CategoryStoryLayBinding
import com.srishti.sda.model.CategoryWithStories

class StoryCategoryAdapter(private val categories: List<CategoryWithStories>, private val context: Context) :
    RecyclerView.Adapter<StoryCategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(private val binding: CategoryStoryLayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryWithStories: CategoryWithStories) {
            binding.tvStoryHeader.text = categoryWithStories.categoryId

            // Set up child RecyclerView for stories
            binding.rvStoryItem.layoutManager = LinearLayoutManager(binding.root.context)
            binding.rvStoryItem.adapter = UserStoryAdapter(categoryWithStories.stories, binding.root.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryStoryLayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
        Toast.makeText(context, "Story Clicked"+categories[position].categoryId, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int = categories.size
}