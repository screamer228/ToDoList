package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var plug: LinearLayout
    private lateinit var adapter: MyAdapter
    private lateinit var db: AppDatabase
    private lateinit var todoLiveData: LiveData<List<ToDoItem>>
    private lateinit var data: List<ToDoItem>

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
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedItem: ToDoItem =
                    data.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                data.toMutableList().removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                Snackbar.make(recyclerView, "Удалено " + deletedItem.title, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Вернуть",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            data.toMutableList().add(position, deletedItem)

                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                        }).show()
                    deleteItem(deletedItem)
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(recyclerView)

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

    fun deleteItem(item: ToDoItem){
        db.todoDao().deleteItem(item)
    }

    override fun itemClicked(item: ToDoItem) {
        val dialog = CustomDialog(this, false, item)
        dialog.show()
    }

}
