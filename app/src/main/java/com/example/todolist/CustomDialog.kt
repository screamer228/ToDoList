package com.example.todolist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CustomDialog(private val activity: MainActivity, context: Context) : Dialog(context) {

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

        okButton.setOnClickListener {
            val inputTitleResult = inputFieldTitle.text.toString()
            val inputDescriptionResult = inputFieldDescription.text.toString()
            val inputTimeResult = inputFieldTime.text.toString().toInt()
            val newItem = ToDoItem(inputTitleResult, inputDescriptionResult, inputTimeResult)
            activity.addItem(newItem)
            dismiss()
        }
        closeButton.setOnClickListener {
            dismiss()
        }
    }
}