package com.example.todolist.repository

import com.example.todolist.model.ToDoItem

interface PrefsRepository {
    fun getToDoItem() : ToDoItem
    fun saveDataInPrefs(key: String, value: String)
}