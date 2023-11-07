package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var plug: LinearLayout
    private lateinit var adapter: MyAdapter
    private lateinit var db: AppDatabase
    private lateinit var todoLiveData: LiveData<List<ToDoItem>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_recycler_view)
        fab = findViewById(R.id.main_fab)
        plug = findViewById(R.id.main_plug)

        fab.setOnClickListener {
            val dialog = CustomDialog(this, true, null)
            dialog.show()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MyAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries().build()

        todoLiveData = db.todoDao().getAllItems()

        todoLiveData.observe(this, Observer {
            adapter.updateList(it)
            screenDataValidation(it)
        })
    }

    private fun screenDataValidation(list: List<ToDoItem>) {
        if (list.isEmpty()){
            plug.visibility = VISIBLE
            recyclerView.visibility = INVISIBLE
        }
        else {
            plug.visibility = INVISIBLE
            recyclerView.visibility = VISIBLE
        }
    }

    fun addItem(item: ToDoItem){
        plug.visibility = INVISIBLE
        recyclerView.visibility = VISIBLE
        db.todoDao().insertItem(item)
    }

    fun updateItem(item: ToDoItem){
        db.todoDao().updateItem(item)
    }

    override fun itemClicked(item: ToDoItem) {
        val dialog = CustomDialog(this, false, item)
        dialog.show()
    }

}
