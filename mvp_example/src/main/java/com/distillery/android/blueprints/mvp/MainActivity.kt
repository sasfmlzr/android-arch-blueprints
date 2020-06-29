package com.distillery.android.blueprints.mvp

import android.os.Bundle
import com.distillery.android.blueprints.mvp.architecture.BaseActivity
import com.distillery.android.blueprints.mvp.feature.todo.ToDoFragment

class MainActivity : BaseActivity(R.layout.activity_todo) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, ToDoFragment.newInstance())
                    .commitNow()
        }
        supportActionBar?.title = getString(R.string.toolbar_title)
    }
}
