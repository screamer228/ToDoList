package com.example.todolist

import com.example.todolist.model.ToDoItem

interface OnItemClicked {
    fun itemClicked(item: ToDoItem)
}