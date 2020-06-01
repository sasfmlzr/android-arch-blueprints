package com.distillery.android.blueprints.mvp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.mvp_example.databinding.ItemTodoBinding

class TODOListAdapter : ListAdapter<ToDoModel, TODOListAdapter.TODOViewHolder>(TODOListDiffCallback()) {

    class TODOViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(toDoModel: ToDoModel) {
            binding.titleTextView.text = toDoModel.title
            binding.descriptionTextView.text = toDoModel.description
            binding.createdAtTextView.text = toDoModel.createdAt.toString()
            binding.completedAtTextView.text = toDoModel.completedAt.toString()
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
        holder.bind(getItem(position))
    }
}
