package com.example.todolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ToDoItem(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo (name = "titleColumn") val title : String,
    val description : String,
    val time : Int
) {
    @Ignore
constructor(title: String, description: String, time: Int) : this(0, title, description, time)
}
