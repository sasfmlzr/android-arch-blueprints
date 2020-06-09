package com.distillery.android.blueprints.mvp.adapter

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.distillery.android.ui.databinding.ItemTodoBinding
import com.distillery.android.domain.models.ToDoModel

class TODOListAdapter(
    private val onDeleteClickListener: (toDoModel: ToDoModel) -> Unit,
    private val onCompleteClickListener: (toDoModel: ToDoModel) -> Boolean
) :
        ListAdapter<ToDoModel, TODOListAdapter.TODOViewHolder>(TODOListDiffCallback()) {

    class TODOViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            toDoModel: ToDoModel,
            onDeleteClickListener: (toDoModel: ToDoModel) -> Unit,
            onCompleteClickListener: (toDoModel: ToDoModel) -> Boolean
        ) {
            binding.titleTextView.text = toDoModel.title
            binding.descriptionTextView.text = toDoModel.description
            binding.deleteButton.setOnClickListener {
                onDeleteClickListener(toDoModel)
            }
            binding.completedCheckBox.isChecked = toDoModel.completedAt != null
            binding.completedCheckBox.setOnClickListener {
                if (!binding.completedCheckBox.isChecked) {
                    binding.completedCheckBox.isChecked = true
                } else {
                    binding.completedCheckBox.isChecked = false
                    onCompleteClickListener(toDoModel)
                }
            }
            if (toDoModel.completedAt != null) {
                binding.titleTextView.paintFlags = binding.titleTextView.paintFlags or STRIKE_THRU_TEXT_FLAG
                binding.descriptionTextView.paintFlags = binding.descriptionTextView.paintFlags or STRIKE_THRU_TEXT_FLAG
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TODOViewHolder {
        return TODOViewHolder(
                ItemTodoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: TODOViewHolder, position: Int) {
        holder.bind(getItem(position), onDeleteClickListener, onCompleteClickListener)
    }
}
