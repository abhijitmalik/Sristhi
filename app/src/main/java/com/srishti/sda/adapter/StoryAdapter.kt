package com.srishti.sda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srishti.sda.databinding.ItemStoryBinding
import com.srishti.sda.model.Story
import java.text.SimpleDateFormat
import java.util.Locale

class StoryAdapter(private val storyType: Int) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var stories = mutableListOf<Story>()

    companion object {
        const val FEATURED = 1
        const val TRENDING = 2
        const val RECENT = 3
        const val EDITORS_CHOICE = 4
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.apply {
                storyTitle.text = story.title
                storyPreview.text = story.story
                authorName.text = story.author
                storyCategory.text = story.category
                likes.text = "${story.likes}"
                storyDate.text =story.posted


                   // SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(story.posted)

                // Load image using Glide
                Glide.with(itemView.context)
                    .load(story.imageUrl)
                    .centerCrop()
                    .into(storyImage)

                // Handle like button click
                btnLike.setOnClickListener {
                    // Implement like functionality
                }

                // Handle share button click
                btnShare.setOnClickListener {
                    // Implement share functionality
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
