package com.example.todolist.data.repository

import com.example.todolist.model.ToDoItem

interface PrefsRepository {
    fun getToDoItem() : ToDoItem
    fun saveDataInPrefs(key: String, value: String)
}