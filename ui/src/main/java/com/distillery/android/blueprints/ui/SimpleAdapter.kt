package com.distillery.android.blueprints.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * this is a simple generic recyclerview adapter with ViewBinding support
 * which takes T (List Item Type) and S (ViewBinding Object) as generic types
 *
 * Example Code:
 * <code><pre><p>
 *      val adapter = SimpleAdapter<DataModel, ItemViewBinding>(
 *          itemList,
 *          { parent ->
 *                ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
 *          },
 *          { item ->
 *                textView.text = item.title
 *          }
 *      )
 *</code></pre></p>
 * @param list generic list to recyclerview, default empty
 * @param getViewBindingForItemV lambda to pass ViewBinding object
 * @param setDataToItemV  - use this extension fun lambda to set data to item view
 *              ViewBinding - S.( T - Object for the current position )
 */
class SimpleAdapter<T, S : ViewBinding>(
    var list: List<T> = emptyList(),
    private val getViewBindingForItemV: (parent: ViewGroup) -> S,
    private val setDataToItemV: S.(t: T) -> Unit
) : RecyclerView.Adapter<SimpleAdapter.CustomViewHolder<S>>() {

    /**
     * invoking getViewBindingForItemV lambda with parent view to get
     * ViewBinding Object and creates ViewHolder using that
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder<S> {
        return CustomViewHolder(getViewBindingForItemV(parent))
    }

    /**
     *  invoking lambda with viewBinding and item object (for current position)
     *  for client to set view elements state and data
     */
    override fun onBindViewHolder(holder: CustomViewHolder<S>, position: Int) {
        setDataToItemV(holder.p, list[position]) //
    }

    override fun getItemCount(): Int = list.size

    /**
     * Generic viewHolder class that gets ViewBinding object and retains it  as property
     * this retained viewBinding property later will be invoked to set data to view elements
     * @param p ViewBinding object to retain and get root view.
     */
    class CustomViewHolder<P : ViewBinding>(val p: P) : RecyclerView.ViewHolder(p.root)
}
