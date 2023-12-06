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
    //var isChecked: Boolean = false
) {
    @Ignore
constructor(title: String, description: String) : this(0, title, description)
}
