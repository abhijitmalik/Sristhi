package com.srishti.sda.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srishti.sda.R
import com.srishti.sda.fragment.Story

class StoryAdapter(private val stories: List<Story>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val authorTextView: TextView = view.findViewById(R.id.authorTextView)
        val likesTextView: TextView = view.findViewById(R.id.likesTextView)
        val storyTextView: TextView = view.findViewById(R.id.storyTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.authorTextView.text = "Author: ${story.author}"
        holder.likesTextView.text = "Likes: ${story.likes}"
        holder.storyTextView.text = story.story
    }

    override fun getItemCount(): Int {
        return stories.size
    }
}
