package com.srishti.sda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.srishti.sda.R
import com.srishti.sda.model.Book

class BookAdapter(
    private val books: List<Book>,
    private val onBookClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
    ) {
        private val bookCover = itemView.findViewById<ImageView>(R.id.bookCover)

        fun bind(book: Book) {
            Glide.with(itemView.context)
                .load(book.coverUrl)
                .placeholder(R.drawable.whats)
                .into(bookCover)

            itemView.setOnClickListener { onBookClick(book) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BookViewHolder(parent)

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount() = books.size
} 