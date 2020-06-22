package com.distillery.android.blueprints.mvvm

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * this is a simple generic recyclerview adapter.
 * this is using viewbinding in the Viewholder to save view object
 * @param list generic list to recyclerview, default empty
 * @param getViewBindingForItemV is lambda to pass viewbinding object
 * @param setDataToItemV is lambda to set data to item views
 */
class SimpleAdapter<T, S : ViewBinding>(
    var list: List<T> = emptyList(),
    private val getViewBindingForItemV: (parent: ViewGroup) -> S,
    private val setDataToItemV: S.(t: T) -> Unit
) : RecyclerView.Adapter<SimpleAdapter.CustomViewHolder<S>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<S> {
        return CustomViewHolder(getViewBindingForItemV(parent))
    }

    override fun onBindViewHolder(holder: CustomViewHolder<S>, position: Int) {
        setDataToItemV(holder.p, list[position])
    }

    override fun getItemCount(): Int = list.size

    class CustomViewHolder<P : ViewBinding>(val p: P) : RecyclerView.ViewHolder(p.root)
}

enum class ItemAction {
    COMPLETED, DELETED
}
