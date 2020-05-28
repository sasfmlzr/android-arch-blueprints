package com.distillery.android.blueprints.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.distillery.android.blueprints.mvp.architecture.BaseFragment
import com.distillery.android.blueprints.mvp.presenter.PresenterProvider
import com.distillery.android.blueprints.mvp.preview.TODOListAdapter
import com.distillery.android.mvp_example.databinding.FragmentTodoBinding

class MainFragment : BaseFragment<FragmentTodoBinding>() {
    override val presenter: MainPresenter = PresenterProvider().getPresenter(this) as MainPresenter

    private lateinit var adapter: TODOListAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = presenter.getList()

        adapter = TODOListAdapter(list)
        binding.todoList.adapter = adapter
        adapter.submitList(list)
    }
}
