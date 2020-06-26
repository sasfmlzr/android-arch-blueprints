package com.distillery.android.blueprints.mvi

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.distillery.android.blueprints.mvi.todo.repo.model.TodoModel
import com.distillery.android.blueprints.mvi.todo.state.TodoState
import com.distillery.android.blueprints.mvi.todo.viewmodel.TodoIntent
import com.distillery.android.blueprints.mvi.todo.viewmodel.TodoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MviActivity : AppCompatActivity() {

    private val todoViewModel: TodoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configureObservers()
        configureListeners()
    }

    private fun configureListeners() {
        val button = Button(this)
        button.setOnClickListener {
            todoViewModel.proccessIntents(flow { emit(TodoIntent.SaveTodo) })
        }
    }

    private fun configureObservers() {
        todoViewModel.todoState
                .onEach { state ->
                    handleState(state)
                }
    }

    private fun handleState(state: TodoState<List<TodoModel>>) {
        when (state) {
            is TodoState.LoadingState -> {
            }
            is TodoState.DataState -> {
            }
            is TodoState.ErrorState -> {
            }
            is TodoState.SaveTodoState -> {
            }
            is TodoState.DeleteState -> {
            }
            is TodoState.ConfirmationState -> {
            }
        }
    }
}
