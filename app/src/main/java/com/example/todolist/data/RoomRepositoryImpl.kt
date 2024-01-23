package com.example.todolist.data

import com.example.todolist.RoomRepository
import com.example.todolist.ToDoItem
import com.example.todolist.room.ToDoDao
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao)
    : RoomRepository {

    override fun getAllItems() : List<ToDoItem> {
        return toDoDao.getAllItems()
    }
    override fun insertItem(item: ToDoItem) {
        toDoDao.insertItem(item)
    }
    override fun updateItem(item: ToDoItem) {
        toDoDao.updateItem(item)
    }
    override fun deleteItem(item: ToDoItem) {
        toDoDao.deleteItem(item)
    }

    companion object{
        const val DATABASE_NAME = "database-name"
    }
}