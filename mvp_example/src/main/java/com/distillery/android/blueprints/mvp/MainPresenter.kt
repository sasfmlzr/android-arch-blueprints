package com.distillery.android.blueprints.mvp

import com.distillery.android.blueprints.mvp.architecture.BasePresenter
import com.distillery.android.domain.ToDoRepository
import org.koin.core.inject

class MainPresenter : BasePresenter() {

    private val todoRepo: ToDoRepository by inject()

    fun getList() = listOf("AAA", "BBB", "CCC")
}
