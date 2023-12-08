package com.example.todolist

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class CustomDialog(private val activity: MainActivity, private val isNewItem: Boolean, private val item: ToDoItem?) : Dialog(activity) {

    private var shouldClearPrefs = false

    private lateinit var dialogTitle: TextView
    private lateinit var okButton: Button
    private lateinit var closeButton: Button
    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)

        initViews()

        if (isNewItem) {

            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
            val titleFromPrefs = sharedPref.getString("titleKey", "")
            val descriptionFromPrefs = sharedPref.getString("descriptionKey", "")
            inputFieldTitle.setText(titleFromPrefs)
            inputFieldDescription.setText(descriptionFromPrefs)

            //inputFieldTitle.requestFocus()
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

    private fun initViews() {
        dialogTitle = findViewById(R.id.dialog_title)
        okButton = findViewById(R.id.ok_button)
        closeButton = findViewById(R.id.close_button)
        inputFieldTitle = findViewById(R.id.input_field_title)
        inputFieldDescription = findViewById(R.id.input_field_description)
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
        shouldClearPrefs = true
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return

        with (sharedPref.edit()) {
            putString("titleKey", "")
            putString("descriptionKey", "")
            apply()
            Log.d("prefstesting", "sharedPrefsApplied")
        }

        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        activity.addItem(ToDoItem(inputTitleResult, inputDescriptionResult))
    }

    private fun okUpdateItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        item?.let { ToDoItem(it.id, inputTitleResult, inputDescriptionResult) }
            ?.let { activity.updateItem(it) }
    }

    private fun updateExistingItem() {
        dialogTitle.text = "Редактировать"
        inputFieldTitle.setText(item?.title)
        inputFieldDescription.setText(item?.description)
    }

    override fun onStop() {
        super.onStop()
        if (isNewItem) {
            if (!shouldClearPrefs){
                val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return

                with (sharedPref.edit()) {
                    val inputTitleResult = inputFieldTitle.text.toString()
                    val inputDescriptionResult = inputFieldDescription.text.toString()
                    putString("titleKey", inputTitleResult)
                    putString("descriptionKey", inputDescriptionResult)
                    apply()
                }
            }
        }
    }
}