package com.distillery.android.blueprints.mvp.architecture

import org.koin.core.KoinComponent

abstract class BasePresenter(private val contractView: BaseContractView?) : KoinComponent, Presentable
