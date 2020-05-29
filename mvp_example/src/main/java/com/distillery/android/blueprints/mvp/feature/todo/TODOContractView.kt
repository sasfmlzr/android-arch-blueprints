package com.distillery.android.blueprints.mvp.feature.todo

import android.view.View
import android.widget.Toast
import com.distillery.android.blueprints.mvp.architecture.BaseContractView
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.mvp_example.databinding.FragmentTodoBinding

abstract class TODOContractView(private val binding: FragmentTodoBinding) : BaseContractView {

    override fun startLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.todoList.visibility = View.GONE
    }

    override fun endLoading() {
        binding.progressBar.visibility = View.GONE
        binding.todoList.visibility = View.VISIBLE
    }

    override fun showError(error: String) {
        val toast = Toast.makeText(binding.root.context, error, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun hideError() {
        val toast = Toast.makeText(binding.root.context, "Hide error", Toast.LENGTH_SHORT)
        toast.show()
    }

    abstract fun showToDoList(list: List<ToDoModel>)
}
