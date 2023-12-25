package com.example.todolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todolist.viewModels.DialogFragmentViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class DialogFragmentViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var subject : DialogFragmentViewModel
    private val prefsRepository : PrefsRepository = mock()

    private val todoItemFake : ToDoItem = ToDoItem("TestTitle", "testDescription")

    private val keyTestResult = "keyTestValue"
    private val valueTestValue = "valueTestValue"

    @Before
    fun setup(){
        subject = DialogFragmentViewModel(prefsRepository)
    }

    @Test
    fun getToDoItemFromPrefs_success() {
        `when`(prefsRepository.getToDoItem()).thenReturn(todoItemFake)
        subject.getToDoItemFromPrefs()
        val expected = subject.todoItemResult.value?.title
        val actual = todoItemFake.title
        assertEquals(expected, actual)
    }

    @Test
    fun saveDataInPrefs_success() {

        subject.saveDataInPrefs(keyTestResult, valueTestValue)
        verify(prefsRepository).saveDataInPrefs(keyTestResult, valueTestValue)
    }
}