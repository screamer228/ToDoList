package com.example.todolist.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.repository.PrefsRepository
import com.example.todolist.model.ToDoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DialogFragmentViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository
)
    : ViewModel() {

    private val todoItem : MutableLiveData<ToDoItem> = MutableLiveData()
    val todoItemResult : LiveData<ToDoItem> = todoItem

    fun getToDoItemFromPrefs() {
        val result = prefsRepository.getToDoItem()
        todoItem.postValue(result)
    }
    fun saveDataInPrefs(key: String, value: String) {
        prefsRepository.saveDataInPrefs(key, value)
    }
}