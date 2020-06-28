package com.distillery.android.blueprints.mvi.todo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.distillery.android.blueprints.mvi.R
import com.distillery.android.ui.databinding.FragmentTodoBinding

class TodoListFragment : Fragment() {

    lateinit var binding: FragmentTodoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.buttonAdd.setOnClickListener {
            navigateToAddItemFragment()
        }
    }

    private fun navigateToAddItemFragment() {
        parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container, AddTodoItemFragment.newInstance())
                .commit()
    }

    companion object {
        fun instance() = TodoListFragment()
    }
}
