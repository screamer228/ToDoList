package com.example.todolist

import android.app.Application
import android.content.SharedPreferences
import com.example.todolist.data.PrefsRepositoryImpl
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PrefsRepositoryImplTest {

    private lateinit var subject : PrefsRepositoryImpl
    private val sharedPreferences : SharedPreferences = mock()

    @Before
    fun setup(){
        subject = PrefsRepositoryImpl(sharedPreferences)
    }

    @Test
    fun getToDoItem_success() : ToDoItem {
        return subject.getToDoItem()
    }
}