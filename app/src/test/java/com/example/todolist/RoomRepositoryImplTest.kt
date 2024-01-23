package com.example.todolist

import com.example.todolist.data.RoomRepositoryImpl
import com.example.todolist.room.ToDoDao
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class RoomRepositoryImplTest {

    private lateinit var subject : RoomRepositoryImpl
    private val toDoDao : ToDoDao = mock()

    @Before
    fun setup(){
        subject = RoomRepositoryImpl(toDoDao)
    }

    @Test
    fun getAllItems_success() : List<ToDoItem> {
        return subject.getAllItems()
    }
}