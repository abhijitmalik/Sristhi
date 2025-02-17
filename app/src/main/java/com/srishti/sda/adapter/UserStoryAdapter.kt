package com.srishti.sda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.srishti.sda.databinding.ItemStoryBinding
import com.srishti.sda.databinding.StoryLayBinding
import com.srishti.sda.model.StoryDataModel

class UserStoryAdapter(private val stories: List<StoryDataModel>, private val context: Context) :
    RecyclerView.Adapter<UserStoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(private val binding: StoryLayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: StoryDataModel) {
            binding.tvStoryName.text = story.story
//            binding.text = "by ${story.storyAuthor}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = StoryLayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(stories[position])
        Toast.makeText(context, "Story Clicked"+stories[position].story, Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int = stories.size
}