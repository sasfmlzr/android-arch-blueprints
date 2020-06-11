package com.distillery.android.blueprints.mvp

import android.os.Bundle
import com.distillery.android.blueprints.mvp.architecture.BaseActivity
import com.distillery.android.blueprints.mvp.feature.todo.ToDoFragment
import com.distillery.android.mvp_example.R

class MainActivity : BaseActivity() {
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ToDoFragment.newInstance())
                    .commitNow()
        }
        supportActionBar?.title = getString(R.string.toolbar_title)
    }
}
