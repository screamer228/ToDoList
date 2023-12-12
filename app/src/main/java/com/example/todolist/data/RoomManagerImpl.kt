package com.example.todolist.data

import android.app.Application
import androidx.room.Room
import com.example.todolist.RoomManager
import com.example.todolist.ToDoItem
import com.example.todolist.room.AppDatabase

class RoomManagerImpl(app : Application) : RoomManager {

    private var db = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "database-name"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    override fun getAllItems() : List<ToDoItem> {
        return db.todoDao().getAllItems()
    }
    override fun insertItem(item: ToDoItem) {
        db.todoDao().insertItem(item)
    }
    override fun updateItem(item: ToDoItem) {
        db.todoDao().updateItem(item)
    }
    override fun deleteItem(item: ToDoItem) {
        db.todoDao().deleteItem(item)
    }
}