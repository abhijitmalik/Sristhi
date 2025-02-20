package com.srishti.sda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srishti.sda.databinding.ItemStoryListBinding
import com.srishti.sda.fragment.Story

class StoryListAdapter : RecyclerView.Adapter<StoryListAdapter.StoryViewHolder>() {
    private var stories = mutableListOf<Story>()

    inner class StoryViewHolder(private val binding: ItemStoryListBinding) : 
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(story: Story) {
            binding.apply {
                authorTextView.text = "Author: ${story.author}"
                likesTextView.text = "Likes: ${story.likes}"
                storyTextView.text = story.story

                // Load image using Glide
                Glide.with(itemView.context)
                    .load(story.ImageURL)
                    .centerCrop()
                    .into(storyImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryListBinding.inflate(
            LayoutInflater.from(parent.context), 
            parent, 
            false
        )
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(stories[position])
    }

    override fun getItemCount() = stories.size

    fun updateStories(newStories: List<Story>) {
        stories.clear()
        stories.addAll(newStories)
        notifyDataSetChanged()
    }
} 