package com.distillery.android.blueprints.mvp.presenter

import com.distillery.android.blueprints.mvp.MainFragment
import com.distillery.android.blueprints.mvp.MainPresenter
import com.distillery.android.blueprints.mvp.architecture.BasePresenter
import com.distillery.android.blueprints.mvp.architecture.BasePresenterProvider
import com.distillery.android.blueprints.mvp.architecture.Presentable

class PresenterProvider : BasePresenterProvider {
    override fun getPresenter(fragment: Presentable): BasePresenter {
        when (fragment) {
            is MainFragment -> return MainPresenter()
            else -> throw NullPointerException()
        }
    }
}
