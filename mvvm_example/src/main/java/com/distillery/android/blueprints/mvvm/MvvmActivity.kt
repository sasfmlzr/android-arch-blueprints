package com.distillery.android.blueprints.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.distillery.android.blueprints.mvvm.viewmodel.ToDoViewModel
import com.distillery.android.mvvm_example.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MvvmActivity : AppCompatActivity() {

    private val todoViewModel by viewModel<ToDoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvvm_activity)
        todoViewModel.getToDoList()
    }
}
