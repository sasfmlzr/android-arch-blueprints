package com.distillery.android.blueprints.mvp.feature.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.distillery.android.blueprints.mvp.PresenterProvider
import com.distillery.android.blueprints.mvp.architecture.BaseFragment
import com.distillery.android.blueprints.mvp.adapter.TODOListAdapter
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.mvp_example.databinding.FragmentTodoBinding

class TODOFragment : BaseFragment<FragmentTodoBinding, TODOContractView>() {

    companion object {
        private const val TODO_MODEL_BUNDLE_KEY = "todo_key"
        fun newInstance() = TODOFragment()
    }

    private lateinit var adapter: TODOListAdapter
    private var todoModel: TODOModel? = null
    override val presenterView: TODOContractView by lazy {
        object : TODOContractView(binding) {
            override fun showToDoList(list: List<ToDoModel>) {
                todoModel = TODOModel(list)
                updateAdapter()
            }
        }
    }

    override val presenter: TODOPresenter by lazy { PresenterProvider().getPresenter(this) as TODOPresenter }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            presenter.getToDoList()
        } else {
            todoModel = savedInstanceState.getParcelable(TODO_MODEL_BUNDLE_KEY)!!
            updateAdapter()
        }
        return binding.root
    }

    private fun updateAdapter() {
        if (binding.todoList.adapter == null) {
            adapter = TODOListAdapter(todoModel?.toDoList!!)
            binding.todoList.adapter = adapter
        }
        adapter.submitList(todoModel!!.toDoList)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(TODO_MODEL_BUNDLE_KEY, todoModel)
        super.onSaveInstanceState(outState)
    }
}
