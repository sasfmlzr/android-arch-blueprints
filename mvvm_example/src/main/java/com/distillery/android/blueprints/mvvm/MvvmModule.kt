package com.distillery.android.blueprints.mvvm

import com.distillery.android.blueprints.mvvm.viewmodel.ToDoViewModel
import com.distillery.android.domain.FakeToDoRepository
import com.distillery.android.domain.ToDoRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mvvmModule = module {
    viewModel { ToDoViewModel(get()) }
    single<ToDoRepository> { FakeToDoRepository(get()) }
}
