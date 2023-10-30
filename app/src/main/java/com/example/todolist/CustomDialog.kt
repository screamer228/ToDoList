package com.example.todolist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.todolist.MainActivity

class CustomDialog(context: Context) : Dialog(context) {
//    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    private lateinit var okButton: Button
    private lateinit var closeButton: Button
    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText
    private lateinit var inputFieldTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)

        okButton = findViewById(R.id.ok_button)
        closeButton = findViewById(R.id.close_button)
        inputFieldTitle = findViewById(R.id.input_field_title)
        inputFieldDescription = findViewById(R.id.input_field_description)
        inputFieldTime = findViewById(R.id.input_field_time)

        //val activity = MainActivity

        okButton.setOnClickListener {
            val inputTitleResult = inputFieldTitle.text.toString()
            val inputDescriptionResult = inputFieldTitle.text.toString()
            val inputTimeResult = inputFieldTitle.text.toString().toInt()
            //activity.addItem(ToDoItem(0,inputTitleResult, inputDescriptionResult, inputTimeResult))
        }
        closeButton.setOnClickListener {
            dismiss()
        }
    }
}