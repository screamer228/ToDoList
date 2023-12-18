package com.example.todolist

interface RoomRepository {
    fun getAllItems() : List<ToDoItem>
    fun insertItem(item: ToDoItem)
    fun updateItem(item: ToDoItem)
    fun deleteItem(item: ToDoItem)
}