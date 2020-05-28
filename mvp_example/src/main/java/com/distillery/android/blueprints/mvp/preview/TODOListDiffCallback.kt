package com.distillery.android.blueprints.mvp.preview

import androidx.recyclerview.widget.DiffUtil

class TODOListDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(p0: String, p1: String): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: String, p1: String): Boolean {
        return p0 == p1
    }
}
