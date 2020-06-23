package com.distillery.android.blueprints.mvp.architecture

import org.koin.core.KoinComponent

abstract class BasePresenter<T>(open var view: T) : KoinComponent, Presentable
