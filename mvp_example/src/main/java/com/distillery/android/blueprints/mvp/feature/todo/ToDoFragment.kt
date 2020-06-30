package com.distillery.android.blueprints.mvp.feature.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.distillery.android.blueprints.mvp.architecture.BaseFragment
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.ui.databinding.FragmentTodoBinding
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class ToDoFragment : BaseFragment<FragmentTodoBinding, ToDoView>() {

    companion object {
        private const val TODO_MODEL_BUNDLE_KEY = "todo_key"
        fun newInstance() = ToDoFragment()
    }

    override val presenterView: ToDoView by lazy {
        object : ToDoView(binding) {
            override fun showToDoList(list: List<ToDoModel>) {
                presenter.toDoPresentationModel = ToDoPresentationModel(list)
                presenter.onUpdateAdapters()
            }
        }
    }

    override val presenter: ToDoPresenter by inject { parametersOf(presenterView) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        presenter.onChangeView(presenterView)
        if (savedInstanceState == null) {
            presenter.onFetchToDo()
        } else {
            presenter.toDoPresentationModel = savedInstanceState.getParcelable(TODO_MODEL_BUNDLE_KEY)!!
            presenter.onUpdateAdapters()
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(TODO_MODEL_BUNDLE_KEY, presenter.toDoPresentationModel)
        super.onSaveInstanceState(outState)
    }
}
