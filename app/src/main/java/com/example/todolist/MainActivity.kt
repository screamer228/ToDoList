package com.example.todolist

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.adapters.MyAdapter
import com.example.todolist.model.ToDoItem
import com.example.todolist.viewModels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnItemClicked {

    private val mainViewModel : MainViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var plug: LinearLayout
    private lateinit var adapter: MyAdapter
    private lateinit var data: List<ToDoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        swipeImplementation()

        observers()

        fab.setOnClickListener {
            val dialogFragment = DialogFragment(true, null)
            dialogFragment.show(supportFragmentManager, "Dialog Fragment")
        }

        mainViewModel.getAllData{

        }
    }

    private fun initViews(){
        recyclerView = findViewById(R.id.main_recycler_view)
        fab = findViewById(R.id.main_fab)
        plug = findViewById(R.id.main_plug)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

    }

    private fun observers() {
        mainViewModel.todoItemListResult.observe(this, Observer {
            data = it
            adapter.updateList(it)
            screenDataValidation(it)
        })
    }

    private fun screenDataValidation(list: List<ToDoItem>) {
        if (list.isEmpty()){
            setupPlug(showPlug = true, showRecycler = false)
        }
        else {
            setupPlug(showPlug = false, showRecycler = true)
        }
    }

    private fun setupPlug(showPlug: Boolean, showRecycler: Boolean){
        plug.isVisible = showPlug
        recyclerView.isVisible = showRecycler
    }

    override fun itemClicked(item: ToDoItem) {
        val dialogFragment = DialogFragment(false, item)
        dialogFragment.show(supportFragmentManager, "Dialog Fragment")
    }

    //логика удаления элемента из списка
    private fun swipeImplementation() {
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.swipe_remove_icon)
        val intrinsicWidth = deleteIcon?.intrinsicWidth
        val intrinsicHeight = deleteIcon?.intrinsicHeight
        val background = ColorDrawable()
        val backgroundColor = Color.parseColor("#f44336")

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

                //data.toMutableList().removeAt(position)
                //adapter.notifyItemRemoved(position)

                Snackbar.make(recyclerView, "Удалено " + deletedItem.title, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo), View.OnClickListener {
                        adapter.restoreItem(position, deletedItem)
                        //data.toMutableList().add(position, deletedItem)
                        mainViewModel.insertItem(deletedItem)
                        //adapter.notifyItemInserted(position)

                    }).show()
                mainViewModel.deleteItem(deletedItem)
            }
        }).attachToRecyclerView(recyclerView)
    }
}
