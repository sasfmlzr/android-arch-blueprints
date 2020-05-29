package com.distillery.android.blueprints.mvp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.distillery.android.domain.models.ToDoModel

class TODOListDiffCallback : DiffUtil.ItemCallback<ToDoModel>() {
    override fun areItemsTheSame(p0: ToDoModel, p1: ToDoModel): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: ToDoModel, p1: ToDoModel): Boolean {
        return p0 == p1
    }
}
