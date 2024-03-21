package com.example.todolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todolist.model.ToDoItem
import com.example.todolist.data.repository.RoomRepository
import com.example.todolist.presentation.viewModels.MainViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject : MainViewModel
    private val roomRepository : RoomRepository = mock()

    private val mockItemOne = ToDoItem("testTitleOne", "testDescriptionOne")
    private val mockItemTwo = ToDoItem("testTitleTwo", "testDescriptionTwo")

    private val mockList = listOf(mockItemOne, mockItemTwo)

    @Before
    fun setup(){
        subject = MainViewModel(roomRepository)
    }

    @Test
    fun getAllData_success() {
        Mockito.`when`(roomRepository.getAllItems()).thenReturn(mockList)
        subject.getAllData()
        val expected = 2
        val actual = subject.todoItemListResult.value?.size
        assertEquals(expected, actual)
    }
}