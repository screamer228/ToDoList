package com.example.todolist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.todolist.data.PrefsManagerImpl

class DialogViewModel(app : Application) : AndroidViewModel(app) {

    val prefsManager : PrefsManager = PrefsManagerImpl(app)



}