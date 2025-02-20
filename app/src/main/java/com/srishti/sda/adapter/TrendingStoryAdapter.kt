package com.srishti.sda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.srishti.sda.R
import com.srishti.sda.utils.Constants

class TrendingStoryAdapter(var list:List<String>, var context: android.content.Context):RecyclerView.Adapter<TrendingStoryAdapter.TreandngStoryViewHolder>() {


    lateinit var arraylist:List<String>
    lateinit var context1: Context
    init {
        arraylist=list
        context1=context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingStoryAdapter.TreandngStoryViewHolder {

        val  view= LayoutInflater.from(parent.context).inflate(R.layout.treadingstory_item,parent,false)
        return TreandngStoryViewHolder(view)


    }

    override fun onBindViewHolder(holder: TrendingStoryAdapter.TreandngStoryViewHolder, position: Int) {
       /* if (arraylist[position].equals(Constants.selcectedCategory)){

        }else{

        }*/
        holder.TdrText.text=arraylist[position]
        holder.TdrText.setOnClickListener{
            Constants.selcectedCategory=arraylist[position]
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int=arraylist.size
    class TreandngStoryViewHolder(view:View ):RecyclerView.ViewHolder(view) {

        val TdrText=view.findViewById<TextView>(R.id.tv_TreadingStory)




    }

}