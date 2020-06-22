package com.distillery.android.blueprints.mvvm.fragments

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.distillery.android.blueprints.mvvm.ItemAction
import com.distillery.android.blueprints.mvvm.SimpleAdapter
import com.distillery.android.blueprints.mvvm.TodoViewModel
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.ui.databinding.FragmentTodoBinding
import com.distillery.android.ui.databinding.ItemTodoBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ToDoListFragment : Fragment() {
    // this  view model will be shared with  AddTodoFragment with Activity scope
    private val todoViewModel: TodoViewModel by sharedViewModel()
    private lateinit var binding: FragmentTodoBinding
    private val todoListAdapter = getSimpleAdapter(false)
    private val completedListAdapter = getSimpleAdapter(true)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList(binding.todoList, todoListAdapter, todoViewModel.todoListLD)
        setupList(binding.completedTodoList, completedListAdapter, todoViewModel.completedTodoListLD)
        setDividerVisibility()
        binding.buttonAdd.setOnClickListener { navigateToAddItemFragment() }
    }

    /**
     * initiate recycler view with adapter, layout manager and observer
     */
    private fun setupList(
        recyclerView: RecyclerView,
        listAdapter: SimpleAdapter<ToDoModel, ItemTodoBinding>,
        liveData: LiveData<List<ToDoModel>>
    ) {
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        liveData.observe(viewLifecycleOwner, Observer {
            listAdapter.list = it
            listAdapter.notifyDataSetChanged()
            setDividerVisibility()
        })
    }

    /**
     * sets the divider line visibility by checking list items count
     * if the onle of the both list is empty then it hides the line otherwise makes visible
     */
    private fun setDividerVisibility() {
        binding.divider.visibility = if (completedListAdapter.list.isEmpty() || todoListAdapter.list.isEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    /**
     * set data and listeners to the item view on the list
     * this is used for both list adapter (completed and non completed list)
     * using boolean param isCompletedAdapter
     */
    private fun ItemTodoBinding.setDataToItemView(
        todoModel: ToDoModel,
        isCompletedAdapter: Boolean
    ) {
        if (isCompletedAdapter) {
            completedCheckBox.isChecked = true
            completedCheckBox.isEnabled = false
            titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            completedCheckBox.isChecked = false
            completedCheckBox.isEnabled = true
            completedCheckBox.setOnClickListener { onListItemClick(ItemAction.COMPLETED, todoModel) }
        }
        titleTextView.text = todoModel.title
        descriptionTextView.text = todoModel.description
        deleteButton.setOnClickListener { onListItemClick(ItemAction.DELETED, todoModel) }
    }

    /**
     * this fun returns the viewbinding object by inflating the list item view
     */
    private fun getViewBindingForItemV(
        parent: ViewGroup
    ) = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    /**
     * its a common onclick listener for the both list adapter (completed and non completed list) item views
     */
    private fun onListItemClick(
        itemAction: ItemAction,
        toDoModel: ToDoModel
    ): Unit = when (itemAction) {
        ItemAction.COMPLETED -> todoViewModel.completeTodo(toDoModel.uniqueId)
        ItemAction.DELETED -> todoViewModel.deleteTodo(toDoModel.uniqueId)
    }

    /**
     * initiates and returns SimpleAdapter with view binding, setDataToItemView lambda
     * and a flag 'isCompletedAdapter'
     */
    private fun getSimpleAdapter(isCompletedAdapter: Boolean): SimpleAdapter<ToDoModel, ItemTodoBinding> {
        return SimpleAdapter<ToDoModel, ItemTodoBinding>(
                getViewBindingForItemV = ::getViewBindingForItemV
        ) { setDataToItemView(todoModel = it, isCompletedAdapter = isCompletedAdapter) }
    }

    /**
     * navigates to add item fragment using fragment transaction
     */
    private fun navigateToAddItemFragment() {
        // todo yet to implement ... on following PRs
        //        parentFragmentManager.beginTransaction()
        //                .addToBackStack(null)
        //                .replace(R.id.container, AddTodoFragment.newInstance())
        //                .commit()
    }

    companion object {
        fun newInstance(): Fragment {
            return ToDoListFragment()
        }
    }
}
