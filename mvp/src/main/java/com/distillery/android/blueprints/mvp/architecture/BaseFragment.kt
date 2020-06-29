package com.distillery.android.blueprints.mvp.architecture

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import org.koin.core.KoinComponent

abstract class BaseFragment<B : ViewBinding, V : BaseView> : Fragment(), KoinComponent {
    abstract val presenter: BasePresenter<V>
    protected lateinit var binding: B
    abstract val presenterView: V
}
