package com.distillery.android.blueprints.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.distillery.android.blueprints.mvvm.fragments.ToDoListFragment
import com.distillery.android.ui.databinding.ActivityTodoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MvvmActivity : AppCompatActivity() {
    // this  view model will be shared between ToDoListFragment and AddTodoFragment with this Activity scope
    private val todoViewModel: TodoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
                .replace(binding.container.id, ToDoListFragment.newInstance())
                .commit()
    }
}
