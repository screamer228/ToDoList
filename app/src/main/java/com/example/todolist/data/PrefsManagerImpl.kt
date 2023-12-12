package com.example.todolist.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.todolist.PrefsManager
import com.example.todolist.ToDoItem

class PrefsManagerImpl(app : Application) : PrefsManager {

    private val sharedPref : SharedPreferences = app.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    override fun getToDoItem() : ToDoItem {
        val title = sharedPref.getString("titleKey", "") ?: ""
        val description = sharedPref.getString("descriptionKey", "") ?: ""
        return ToDoItem(title, description)
    }

    override fun saveDataInPrefs(key: String, value: String) {
        with(sharedPref.edit()){
            putString(key, value)
            apply()
        }
    }

}