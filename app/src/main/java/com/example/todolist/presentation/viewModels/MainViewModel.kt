package com.example.todolist.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.repository.RoomRepository
import com.example.todolist.model.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {

    private var _todoItemList: MutableLiveData<List<ToDoItem>> = MutableLiveData()
    val todoItemList: LiveData<List<ToDoItem>> = _todoItemList

    fun getAllData() {
        val result = roomRepository.getAllItems()
        _todoItemList.postValue(result)
    }

    fun insertItem(item: ToDoItem) {
        roomRepository.insertItem(item)
        getAllData()
    }

    fun updateItem(item: ToDoItem) {
        roomRepository.updateItem(item)
        getAllData()
    }

    fun deleteItem(item: ToDoItem) {
        roomRepository.deleteItem(item)
//        getAllData()
    }
}