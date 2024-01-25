package com.example.todolist.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.repository.RoomRepository
import com.example.todolist.model.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val roomRepository: RoomRepository
)
    : ViewModel() {

    private var todoItemList : MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemListResult : LiveData<List<ToDoItem>> = todoItemList

    fun getAllData(callback :() -> Unit ) {
        val result = roomRepository.getAllItems()
        todoItemList.postValue(result)
        callback()
    }

    fun insertItem(item: ToDoItem) {
        roomRepository.insertItem(item)
        getAllData {}
    }

    fun updateItem(item: ToDoItem) {
        roomRepository.updateItem(item)
        getAllData{}
    }

    fun deleteItem(item: ToDoItem) {
        roomRepository.deleteItem(item)
        getAllData{}
    }
}