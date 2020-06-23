package com.distillery.android.blueprints.mvp.feature.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.distillery.android.blueprints.mvp.R
import com.distillery.android.blueprints.mvp.adapter.ToDoListAdapter
import com.distillery.android.blueprints.mvp.architecture.BaseFragment
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.ui.databinding.FragmentTodoBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class ToDoFragment : BaseFragment<FragmentTodoBinding, ToDoView>() {

    companion object {
        private const val TODO_MODEL_BUNDLE_KEY = "todo_key"
        fun newInstance() = ToDoFragment()
    }

    private lateinit var uncompletedToDoAdapter: ToDoListAdapter
    private lateinit var completedToDoAdapter: ToDoListAdapter
    private var toDoModel: com.distillery.android.blueprints.mvp.feature.todo.ToDoModel = ToDoModel(listOf())

    override val presenterView: ToDoView by lazy {
        object : ToDoView(binding) {
            override fun showToDoList(list: List<ToDoModel>) {
                toDoModel = ToDoModel(list)
                updateAdapter()
            }
        }
    }

    override val presenter: ToDoPresenter by inject { parametersOf(presenterView) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        binding.buttonAdd.setOnClickListener {
            presenter.addToDo()
        }
        presenter.changeView(presenterView)
        if (savedInstanceState == null) {
            presenter.fetchToDo()
        } else {
            toDoModel = savedInstanceState.getParcelable(TODO_MODEL_BUNDLE_KEY)!!
            updateAdapter()
        }
        return binding.root
    }

    private fun updateAdapter() {
        if (binding.todoList.adapter == null) {
            uncompletedToDoAdapter = createToDoAdapter()
            binding.todoList.adapter = uncompletedToDoAdapter
        }
        if (binding.completedTodoList.adapter == null) {
            completedToDoAdapter = createToDoAdapter()
            binding.completedTodoList.adapter = completedToDoAdapter
        }
        uncompletedToDoAdapter.submitList(toDoModel.toDoList.filter { it.completedAt == null })
        completedToDoAdapter.submitList(toDoModel.toDoList.filter { it.completedAt != null })
    }

    private fun createToDoAdapter() =
            ToDoListAdapter({ todo ->
                presenter.deleteToDo(todo.uniqueId)
                showDeleteSnackbar()
            }, { todo ->
                presenter.completeToDo(todo)
                false
            })

    private fun showDeleteSnackbar() {
        val snackbar = Snackbar.make(binding.root, getString(R.string.snackbar_delete_message), Snackbar.LENGTH_LONG)
        snackbar.setAction(getString(R.string.ok)) {}
        snackbar.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(TODO_MODEL_BUNDLE_KEY, toDoModel)
        super.onSaveInstanceState(outState)
    }
}
