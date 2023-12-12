package com.example.todolist

import androidx.lifecycle.LiveData

interface RoomManager {
    fun getAllItems() : List<ToDoItem>
    fun insertItem(item: ToDoItem)
    fun updateItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
}