package com.distillery.android.blueprints.mvp.feature.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.distillery.android.blueprints.mvp.adapter.TODOListAdapter
import com.distillery.android.blueprints.mvp.architecture.BaseFragment
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.ui.databinding.FragmentTodoBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class TODOFragment : BaseFragment<FragmentTodoBinding, TODOContractView>() {

    companion object {
        private const val TODO_MODEL_BUNDLE_KEY = "todo_key"
        fun newInstance() = TODOFragment()
    }

    private lateinit var uncompletedTODOAdapter: TODOListAdapter
    private lateinit var completedTODOAdapter: TODOListAdapter
    private var todoModel: TODOModel = TODOModel(listOf())

    override val presenterView: TODOContractView by lazy {
        object : TODOContractView(binding) {
            override fun showToDoList(list: List<ToDoModel>) {
                todoModel = TODOModel(list)
                updateAdapter()
            }
        }
    }

    override val presenter: TODOPresenter by inject { parametersOf(presenterView) }

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
            todoModel = savedInstanceState.getParcelable(TODO_MODEL_BUNDLE_KEY)!!
            updateAdapter()
        }
        return binding.root
    }

    private fun updateAdapter() {
        if (binding.todoList.adapter == null) {
            uncompletedTODOAdapter = createTODOAdapter()
            binding.todoList.adapter = uncompletedTODOAdapter
        }
        if (binding.completedTodoList.adapter == null) {
            completedTODOAdapter = createTODOAdapter()
            binding.completedTodoList.adapter = completedTODOAdapter
        }
        uncompletedTODOAdapter.submitList(todoModel.toDoList.filter { it.completedAt == null })
        completedTODOAdapter.submitList(todoModel.toDoList.filter { it.completedAt != null })
    }

    private fun createTODOAdapter() =
            TODOListAdapter({ todo ->
                presenter.deleteToDo(todo.uniqueId)
                showDeleteSnackbar()
            }, { todo ->
                presenter.completeToDo(todo)
                false
            })

    private fun showDeleteSnackbar() {
        val snackbar = Snackbar.make(binding.root, "Item has been deleted", Snackbar.LENGTH_LONG)
        snackbar.setAction("OK") {}
        snackbar.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(TODO_MODEL_BUNDLE_KEY, todoModel)
        super.onSaveInstanceState(outState)
    }
}
