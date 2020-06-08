package com.distillery.android.blueprints.mvp.feature.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.distillery.android.blueprints.mvp.adapter.TODOListAdapter
import com.distillery.android.blueprints.mvp.architecture.BaseFragment
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.ui.databinding.FragmentTodoBinding
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class TODOFragment : BaseFragment<FragmentTodoBinding, TODOContractView>() {

    companion object {
        private const val TODO_MODEL_BUNDLE_KEY = "todo_key"
        fun newInstance() = TODOFragment()
    }

    private lateinit var adapter: TODOListAdapter
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
            presenter.addTodo()
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
            adapter = TODOListAdapter({ todo ->
                todoModel = TODOModel(todoModel.toDoList.filter { it != todo })
                adapter.submitList(todoModel.toDoList)
            }, { todo ->
                presenter.completeToDo(todo)
                false
            })
            binding.todoList.adapter = adapter
        }
        adapter.submitList(todoModel.toDoList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(TODO_MODEL_BUNDLE_KEY, todoModel)
        super.onSaveInstanceState(outState)
    }
}
