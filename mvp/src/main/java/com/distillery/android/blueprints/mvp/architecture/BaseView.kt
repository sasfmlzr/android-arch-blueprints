package com.distillery.android.blueprints.mvp.architecture

interface BaseView {
    fun startLoading()
    fun endLoading()
    fun showError(error: String)
}
