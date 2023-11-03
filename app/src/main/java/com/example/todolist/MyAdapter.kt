package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var mList: MutableList<ToDoItem>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)
        val timeTextView: TextView = itemView.findViewById(R.id.item_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = mList[position].title
        holder.descriptionTextView.text = mList[position].description
        holder.timeTextView.text = mList[position].time.toString()
    }
    fun updateList(updatedList: List<ToDoItem>){
        mList = updatedList.toMutableList()
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return mList.size
    }
}