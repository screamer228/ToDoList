package com.example.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var mList: MutableList<ToDoItem>, private val click: OnItemClicked) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)
        val itemContainer: LinearLayout = itemView.findViewById(R.id.item_container)
        val itemCheckbox: CheckBox = itemView.findViewById(R.id.item_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTextView.text = mList[position].title
        holder.descriptionTextView.text = mList[position].description
        val currentItem = mList[position]

        holder.itemContainer.setOnClickListener {
            click.itemClicked(mList[position])
        }

        holder.itemCheckbox.isChecked = PreferencesManager.loadCheckboxState(holder.itemView.context, position)

        holder.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isChecked = isChecked
            PreferencesManager.saveCheckboxState(holder.itemView.context, position, isChecked)
        }
    }

    fun updateList(updatedList: List<ToDoItem>){
        mList = updatedList.toMutableList()
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return mList.size
    }
}