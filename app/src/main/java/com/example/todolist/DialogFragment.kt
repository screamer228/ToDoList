package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.todolist.model.ToDoItem
import com.example.todolist.repository.PrefsRepositoryImpl.Companion.PREFS_DESCRIPTION_KEY
import com.example.todolist.repository.PrefsRepositoryImpl.Companion.PREFS_TITLE_KEY
import com.example.todolist.viewModels.DialogFragmentViewModel
import com.example.todolist.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogFragment(private val isNewItem: Boolean, private val item: ToDoItem?) : DialogFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val dialogFragmentViewModel: DialogFragmentViewModel by activityViewModels()

    private var shouldClearPrefs = false

    private lateinit var dialogTitle: TextView
    private lateinit var okButton: Button
    private lateinit var closeButton: Button
    private lateinit var inputFieldTitle: EditText
    private lateinit var inputFieldDescription: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.custom_dialog, container, false)

        initViews(view)
        clickListeners()

        if (isNewItem) {
            dialogFragmentViewModel.getToDoItemFromPrefs()
        } else {
            dialogTitle.text = "Редактировать"
            inputFieldTitle.setText(item?.title)
            inputFieldDescription.setText(item?.description)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        observers()
    }

    private fun initViews(view: View) {
        dialogTitle = view.findViewById(R.id.dialog_title)
        okButton = view.findViewById(R.id.ok_button)
        closeButton = view.findViewById(R.id.close_button)
        inputFieldTitle = view.findViewById(R.id.input_field_title)
        inputFieldDescription = view.findViewById(R.id.input_field_description)
    }

    private fun clickListeners() {
        okButton.setOnClickListener {
            okButtonClicker()
        }
        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun observers() {
        dialogFragmentViewModel.todoItemResult.observe(this, Observer {
            if (isNewItem) {
                //if (!shouldClearPrefs) {
                inputFieldTitle.setText(it.title)
                inputFieldDescription.setText(it.description)
                //}
            }
        })
    }

    private fun okButtonClicker() {
        if (isNewItem) {
            okAddItemBeenClicked()
        } else {
            okUpdateItemBeenClicked()
        }
        dismiss()
    }

    private fun okAddItemBeenClicked() {
        shouldClearPrefs = true

        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        mainViewModel.insertItem(ToDoItem(inputTitleResult, inputDescriptionResult))
        inputFieldTitle.text.clear()
        inputFieldDescription.text.clear()
    }

    private fun okUpdateItemBeenClicked() {
        val inputTitleResult = inputFieldTitle.text.toString()
        val inputDescriptionResult = inputFieldDescription.text.toString()
        item?.let { ToDoItem(it.id, inputTitleResult, inputDescriptionResult) }
            ?.let { mainViewModel.updateItem(it) }
    }

    override fun onStop() {
        super.onStop()
        if (isNewItem) {
            //if (!shouldClearPrefs) {
                val inputTitleResult = inputFieldTitle.text.toString()
                val inputDescriptionResult = inputFieldDescription.text.toString()
                dialogFragmentViewModel.saveDataInPrefs(PREFS_TITLE_KEY, inputTitleResult)
                dialogFragmentViewModel.saveDataInPrefs(PREFS_DESCRIPTION_KEY, inputDescriptionResult)
            //}
        }
    }
}