package com.distillery.android.blueprints.mvp.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.distillery.android.mvp_example.databinding.ItemTodoBinding

class TODOListAdapter(private val todoList: List<String>) :
    ListAdapter<String, TODOListAdapter.TODOViewHolder>(TODOListDiffCallback()) {

    class TODOViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TODOViewHolder {
        return TODOViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TODOViewHolder, position: Int) {
        getItem(position).let { todo ->
            holder.binding.textView.text = todo
        }
    }

    override fun getItemCount() = todoList.size
}
