package com.example.todolist.data.repository

import com.example.todolist.model.ToDoItem

interface RoomRepository {

    fun getAllItems() : List<ToDoItem>

    fun insertItem(item: ToDoItem)

    fun updateItem(item: ToDoItem)

    fun deleteItem(item: ToDoItem)
}