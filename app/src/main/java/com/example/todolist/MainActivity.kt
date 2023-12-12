package com.example.todolist

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.todolist.room.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), OnItemClicked {

    private val mMainViewModel : MainViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var plug: LinearLayout
    private lateinit var adapter: MyAdapter
//    private lateinit var db: AppDatabase
//    private lateinit var todoLiveData: LiveData<List<ToDoItem>>
    private lateinit var data: List<ToDoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.main_recycler_view)
        fab = findViewById(R.id.main_fab)
        plug = findViewById(R.id.main_plug)

        fab.setOnClickListener {
            val dialogFragment = DialogFragment(this, true, null)
            dialogFragment.show(supportFragmentManager, "Dialog Fragment")
        }

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MyAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        mMainViewModel.getAllData()
        val tdir = mMainViewModel.todoItemListResult
        Log.d("listCheck", "$tdir")
        mMainViewModel.todoItemListResult.observe(this, Observer {
            Log.d("roomCheck", "$it")
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
            //Log.d("roomCheck", "$it")
        })

        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.swipe_remove_icon)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor("#f44336")
        val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                background.color = backgroundColor
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(canvas)

                val iconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth!!
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(canvas)

                super.onChildDraw(
                    canvas,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val position = viewHolder.adapterPosition
                val deletedItem: ToDoItem =
                    data[position]

                adapter.deleteItem(position)

//                data.toMutableList().removeAt(position)
//                adapter.notifyItemRemoved(position)

                Snackbar.make(recyclerView, "Удалено " + deletedItem.title, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo), View.OnClickListener {
                        adapter.restoreItem(position, deletedItem)
                        //data.toMutableList().add(position, deletedItem)
                        insertItem(deletedItem)
                        //adapter.notifyItemInserted(position)

                    }).show()
                deleteItem(deletedItem)
            }
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

    fun insertItem(item: ToDoItem){
        plug.visibility = INVISIBLE
        recyclerView.visibility = VISIBLE
        mMainViewModel.insertItem(item)
    }

    fun updateItem(item: ToDoItem){
        mMainViewModel.updateItem(item)
    }

    fun deleteItem(item: ToDoItem){
        mMainViewModel.deleteItem(item)
    }

    override fun itemClicked(item: ToDoItem) {
        val dialogFragment = DialogFragment(this, false, item)
        dialogFragment.show(supportFragmentManager, "Dialog Fragment")
    }

    override fun onDestroy() {
        // Убедитесь, что нет явной отписки от LiveData
        mMainViewModel.todoItemListResult.removeObservers(this)

        super.onDestroy()
    }
}
