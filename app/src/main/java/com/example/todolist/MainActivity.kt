package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var plug: LinearLayout
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_recycler_view)
        fab = findViewById(R.id.main_fab)
        plug = findViewById(R.id.main_plug)

        fab.setOnClickListener {
            val dialog = CustomDialog(this)
            dialog.show()
        }

        val data = ArrayList<ToDoItem>()

        if (data.isEmpty()){
            plug.visibility = VISIBLE
            recyclerView.visibility = INVISIBLE
        }
        else {
            plug.visibility = INVISIBLE
            recyclerView.visibility = VISIBLE
        }

        val myAdapter = MyAdapter(data)

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        fun addItem(item: ToDoItem){
            plug.visibility = INVISIBLE
            recyclerView.visibility = VISIBLE
            db.userDao().insertItem(item)
        }
    }
}