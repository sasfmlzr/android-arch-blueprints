package com.distillery.android.blueprints.mvp

import com.distillery.android.blueprints.mvp.feature.todo.TODOFragment
import com.distillery.android.blueprints.mvp.feature.todo.TODOPresenter
import com.distillery.android.blueprints.mvp.architecture.BasePresenter
import com.distillery.android.blueprints.mvp.architecture.BasePresenterProvider
import com.distillery.android.blueprints.mvp.architecture.Presentable

class PresenterProvider : BasePresenterProvider {
    override fun getPresenter(fragment: Presentable): BasePresenter {
        when (fragment) {
            is TODOFragment -> return TODOPresenter(fragment.presenterView)
            else -> throw NullPointerException()
        }
    }
}
