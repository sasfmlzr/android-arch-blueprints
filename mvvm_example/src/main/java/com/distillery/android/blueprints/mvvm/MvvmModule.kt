package com.distillery.android.blueprints.mvvm

import com.distillery.android.blueprints.mvvm.viewmodels.TodoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mvvmModule = module {
    viewModel { TodoListViewModel(get()) }
}
