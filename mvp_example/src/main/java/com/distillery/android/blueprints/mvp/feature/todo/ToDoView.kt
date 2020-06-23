package com.distillery.android.blueprints.mvp.feature.todo

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.distillery.android.blueprints.mvp.architecture.BaseView
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.mvp_example.R
import com.distillery.android.ui.databinding.FragmentTodoBinding

abstract class ToDoView(private val binding: FragmentTodoBinding) : BaseView {

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.todoContent.visibility = View.GONE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.GONE
        binding.todoContent.visibility = View.VISIBLE
    }

    override fun showError(error: String) {
        val toast = Toast.makeText(binding.root.context, error, Toast.LENGTH_SHORT)
        toast.show()
    }

    abstract fun showToDoList(list: List<ToDoModel>)

    fun addToDo(toDoAction: (String, String) -> Unit) {
        AlertDialog.Builder(binding.root.context)
                .setTitle(binding.root.context.getString(R.string.todo_dialog_title))
                .setMessage(binding.root.context.getString(R.string.todo_dialog_message))
                .setView(R.layout.dialog_add_todo)
                .setPositiveButton(binding.root.context.getString(R.string.ok)) { dialog, _ ->
                    val title = (dialog as AlertDialog).findViewById<EditText>(R.id.title)?.text.toString()
                    val description = dialog.findViewById<EditText>(R.id.description)?.text.toString()
                    toDoAction(title, description)
                }
                .setNegativeButton(binding.root.context.getString(R.string.cancel)) { _, _ -> }
                .show()
    }
}
