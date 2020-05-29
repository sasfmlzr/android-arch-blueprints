package com.distillery.android.blueprints.mvp

import android.app.Application
import com.distillery.android.blueprints.mvp.architecture.BasePresenterProvider
import com.distillery.android.domain.FakeToDoRepository
import com.distillery.android.domain.ToDoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MainApplication : Application() {
    private val modules: Module = module {
        single<ToDoRepository> { FakeToDoRepository() }
        single<BasePresenterProvider> { PresenterProvider() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}
