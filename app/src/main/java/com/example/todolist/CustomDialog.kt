package com.example.todolist

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CustomDialog(private val activity: MainActivity, private val isNewItem: Boolean, private val item: ToDoItem?) : Dialog(activity) {

    private lateinit var dialogTitle: TextView
    private lateinit var okButton: Button
    private lateinit var closeButton: Button
    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText
    private lateinit var inputFieldTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)

        dialogTitle = findViewById(R.id.dialog_title)
        okButton = findViewById(R.id.ok_button)
        closeButton = findViewById(R.id.close_button)
        inputFieldTitle = findViewById(R.id.input_field_title)
        inputFieldDescription = findViewById(R.id.input_field_description)
        inputFieldTime = findViewById(R.id.input_field_time)

        if (isNewItem) {
            inputFieldTitle.requestFocus()
        }
        else {
            updateExistingItem()
        }

        okButton.setOnClickListener {
            okButtonClicker()
        }

        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun okButtonClicker() {
        if (isNewItem) {
            okAddItemBeenClicked()
        }
        else {
            okUpdateItemBeenClicked()
        }
        dismiss()
    }

    private fun okAddItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputTimeResult = inputFieldTime.text.toString().toInt()
        activity.addItem(ToDoItem(inputTitleResult, inputDescriptionResult, inputTimeResult))
    }

    private fun okUpdateItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        val inputTimeResult = inputFieldTime.text.toString().toInt()
        item?.let { ToDoItem(it.id, inputTitleResult, inputDescriptionResult, inputTimeResult) }
            ?.let { activity.updateItem(it) }
    }

    private fun updateExistingItem() {
        dialogTitle.text = "Редактировать"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
        inputFieldTime.setText(item?.time.toString())

    }

}