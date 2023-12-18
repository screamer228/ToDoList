package com.example.todolist

interface PrefsRepository {
    fun getToDoItem() : ToDoItem
    fun saveDataInPrefs(key: String, value: String)
}