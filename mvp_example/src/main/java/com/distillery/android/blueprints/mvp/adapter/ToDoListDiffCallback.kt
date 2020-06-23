package com.distillery.android.blueprints.mvp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.distillery.android.domain.models.ToDoModel

class ToDoListDiffCallback : DiffUtil.ItemCallback<ToDoModel>() {
    override fun areItemsTheSame(p0: ToDoModel, p1: ToDoModel): Boolean {
        return p0.uniqueId == p1.uniqueId
    }

    override fun areContentsTheSame(p0: ToDoModel, p1: ToDoModel): Boolean {
        return p0 == p1
    }
}
