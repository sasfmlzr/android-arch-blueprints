package com.distillery.android.blueprints.mvp

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class ToDoCustomView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


   // private val binder: ItemToDoCustomViewBinding by lazy {
  //      ToDoCustomViewBinding.inflate(LayoutInflater.from(context))
  //  }

    //
//
    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
   //     addView(binder.root, params)
    }

    fun populateItem(listener: OnClickListener, text: String) {
    //    binder.text.text = text
    //    binder.root.setOnClickListener(listener)
    }

}
