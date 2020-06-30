package com.distillery.android.blueprints.mvp.feature.todo

import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.distillery.android.blueprints.mvp.R
import com.distillery.android.blueprints.mvp.architecture.BaseView
import com.distillery.android.blueprints.mvp.architecture.WithErrorDisplayer
import com.distillery.android.blueprints.mvp.architecture.WithLoading
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.ui.adapter.ToDoListAdapter
import com.distillery.android.ui.databinding.FragmentTodoBinding
import com.google.android.material.snackbar.Snackbar

abstract class ToDoView(private val binding: FragmentTodoBinding) : BaseView, WithLoading, WithErrorDisplayer {

    private lateinit var uncompletedToDoAdapter: ToDoListAdapter
    private lateinit var completedToDoAdapter: ToDoListAdapter

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

    fun createUncompletedAdapter(adapter: ToDoListAdapter) {
        uncompletedToDoAdapter = adapter
        binding.todoList.adapter = uncompletedToDoAdapter
    }

    fun createCompletedAdapter(adapter: ToDoListAdapter) {
        completedToDoAdapter = adapter
        binding.completedTodoList.adapter = completedToDoAdapter
    }

    fun isUncompletedAdapterExists() = binding.todoList.adapter != null
    fun isCompletedAdapterExists() = binding.completedTodoList.adapter != null

    fun updateUncompletedList(uncompletedToDoModels: List<ToDoModel>) {
        uncompletedToDoAdapter.submitList(uncompletedToDoModels)
    }

    fun updateCompletedList(completedToDoModels: List<ToDoModel>) {
        completedToDoAdapter.submitList(completedToDoModels)
    }

    fun createToDoAdapter(
        onDeleteClickListener: (toDoModel: ToDoModel) -> Unit,
        onCompleteClickListener: (toDoModel: ToDoModel) -> Unit
    ): ToDoListAdapter {
        return ToDoListAdapter(onDeleteClickListener, onCompleteClickListener)
    }

    fun showDeleteSnackbar() {
        val snackbar = Snackbar.make(binding.root, binding.root.context.getString(R.string.snackbar_delete_message),
                Snackbar.LENGTH_LONG)
        snackbar.setAction(binding.root.context.getString(R.string.ok)) {}
        snackbar.show()
    }

    fun setClickListenerForAddButton(onClickListener: (View) -> Unit) {
        binding.buttonAdd.setOnClickListener(onClickListener)
    }
}
