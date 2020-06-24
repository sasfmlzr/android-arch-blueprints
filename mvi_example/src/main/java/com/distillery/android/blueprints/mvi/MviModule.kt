package com.distillery.android.blueprints.mvi

import com.distillery.android.domain.FakeToDoRepository
import com.distillery.android.domain.ToDoRepository
import org.koin.dsl.module

val mviModule = module {
    single<ToDoRepository> { FakeToDoRepository(get()) }
}
