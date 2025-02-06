package com.srishti.sda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.srishti.sda.R
import com.srishti.sda.utils.Constants

class CategoryAdapter(var list: List<String>,var context: Context) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    lateinit var arrlist:List<String>
    lateinit var context1: Context
    init {
        arrlist=list
        this.context1=context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int =arrlist.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        if (arrlist[position].equals(Constants.selcectedCategory)){
            holder.catText.setBackgroundColor(context1.getColor(R.color.orange))
            holder.catText.setTextColor(context1.resources.getColor(R.color.white))
        }else{
            holder.catText.setBackgroundColor(context1.getColor(R.color.white))
            holder.catText.setTextColor(context1.resources.getColor(R.color.black))
        }
        holder.catText.text=arrlist[position]
        holder.catText.setOnClickListener{
            Constants.selcectedCategory=arrlist[position]
            notifyDataSetChanged()
        }
    }
    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val catText: TextView = view.findViewById(R.id.tv_category)
    }
}