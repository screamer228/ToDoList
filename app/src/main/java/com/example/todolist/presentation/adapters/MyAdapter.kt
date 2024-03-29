package com.example.todolist.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.OnItemClicked
import com.example.todolist.R
import com.example.todolist.model.ToDoItem
import com.example.todolist.utils.MyDiffUtil

class MyAdapter(private var dataList: MutableList<ToDoItem>, private val click: OnItemClicked) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)
        val itemContainer: LinearLayout = itemView.findViewById(R.id.item_container)
        //val itemCheckbox: CheckBox = itemView.findViewById(R.id.item_checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.titleTextView.text = currentItem.title
        holder.descriptionTextView.text = currentItem.description

        holder.itemContainer.setOnClickListener {
            click.itemClicked(currentItem)
        }
//        holder.itemCheckbox.isChecked = PreferencesManager.loadCheckboxState(holder.itemView.context, position)
//
//        holder.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
//            currentItem.isChecked = isChecked
//            PreferencesManager.saveCheckboxState(holder.itemView.context, position, isChecked)
//        }
    }

    fun deleteItem(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }
    fun restoreItem(position: Int, item: ToDoItem) {
        dataList.add(position, item)
        notifyItemInserted(position)
    }
    fun updateList(newDataList: List<ToDoItem>) {
        val diffUtil = MyDiffUtil(dataList, newDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        dataList = newDataList.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
}