package com.distillery.android.blueprints.mvp.architecture

/**
 * Presenter provider interface to create some presenters based on BasePresenter.
 * Needs because koin can't work with generic types.
 */
interface BasePresenterProvider {
    fun getPresenter(fragment: Presentable): BasePresenter
}
