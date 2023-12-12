package com.example.todolist

interface PrefsManager {
    fun getToDoItem() : ToDoItem
    fun saveDataInPrefs(key: String, value: String)
}