package com.distillery.android.blueprints.mvp

import android.os.Bundle
import com.distillery.android.blueprints.mvp.architecture.BaseActivity
import com.distillery.android.blueprints.mvp.feature.todo.TODOFragment
import com.distillery.android.mvp_example.R

class MainActivity : BaseActivity() {
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TODOFragment.newInstance())
                    .commitNow()
        }
        supportActionBar?.title = "Todo list"
    }
}
