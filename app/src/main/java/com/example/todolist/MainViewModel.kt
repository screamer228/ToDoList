package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.todolist.data.RoomManagerImpl

class MainViewModel(app : Application) : AndroidViewModel(app) {

    private val roomManager : RoomManager = RoomManagerImpl(app)

    private var todoItemList : MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemListResult : LiveData<List<ToDoItem>> = todoItemList

    fun getAllData() {
        val result = roomManager.getAllItems()
        todoItemList.postValue(result)
    }
    fun insertItem(item: ToDoItem) {
        roomManager.insertItem(item)
        getAllData()
    }
    fun updateItem(item: ToDoItem) {
        roomManager.updateItem(item)
        getAllData()
    }
    fun deleteItem(item: ToDoItem) {
        roomManager.deleteItem(item)
        getAllData()
    }
}