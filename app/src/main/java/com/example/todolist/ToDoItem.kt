package com.example.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDoItem(
    @PrimaryKey val id: Int,
    @ColumnInfo (name = "titleColumn") val title : String,
    val description : String,
    val time : Int
)
