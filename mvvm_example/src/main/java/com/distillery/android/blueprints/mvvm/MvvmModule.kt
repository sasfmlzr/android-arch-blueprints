package com.distillery.android.blueprints.mvvm

import com.distillery.android.domain.FakeToDoRepository
import com.distillery.android.domain.ToDoRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mvvmModule = module {
    viewModel { TodoViewModel(get()) }
    single<ToDoRepository> { FakeToDoRepository(get()) }
}
