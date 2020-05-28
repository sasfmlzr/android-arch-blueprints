package com.distillery.android.blueprints.mvp.architecture

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseFragment<B : ViewBinding> : Fragment(), KoinComponent, Presentable {

    private val basePresenterProvider: BasePresenterProvider by inject()
    abstract val presenter: BasePresenter
    protected lateinit var binding: B
}
