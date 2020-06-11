package com.distillery.android.blueprints.mvp.architecture

interface BaseContractView {
    fun startLoading()
    fun endLoading()
    fun showError(error: String)
}
