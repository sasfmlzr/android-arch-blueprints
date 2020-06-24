package com.distillery.android.blueprints

import android.app.Application
import com.distillery.android.blueprints.mvi.mviModule
import com.distillery.android.blueprints.mvvm.mvvmModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class BluePrintsApplication : Application() {

    val coroutineModule = module {
        factory { CoroutineScope(Dispatchers.IO + SupervisorJob()) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@BluePrintsApplication)
            modules(listOf(
                    coroutineModule,
                    mvvmModule,
                    mviModule
            ))
        }
    }
}
