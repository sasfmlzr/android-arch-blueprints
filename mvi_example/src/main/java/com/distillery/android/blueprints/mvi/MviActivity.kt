package com.distillery.android.blueprints.mvi

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.distillery.android.blueprints.mvi.todo.TodoIntent
import com.distillery.android.blueprints.mvi.todo.TodoListModel
import com.distillery.android.blueprints.mvi.todo.state.ConfirmationCode
import com.distillery.android.blueprints.mvi.todo.state.TodoState
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
            todoViewModel.proccessIntents(flow {
                emit(TodoIntent.SaveTodo("Title", "Description"))
            })
        }
    }

    private fun configureObservers() {
        todoViewModel.todoState
                .onEach { state ->
                    handleState(state)
                }
    }

    private fun handleState(state: TodoState<TodoListModel>) {
        when (state) {
            is TodoState.LoadingState -> {
                // show loader
            }
            is TodoState.DataState -> {
                // hide loader
                state.todoListFlow.completedTodoList // set to pending RecyclerView Adapter
                state.todoListFlow.todoList // set to completed RecyclerView Adapter
            }
            is TodoState.ErrorState -> {
                // hide loader
                // show Error snackbar
            }
            is TodoState.ConfirmationState -> {
                // hide loader
                when (state.confirmationCode) {
                    is ConfirmationCode.SAVED -> {
                        // show snackbar
                    }
                    is ConfirmationCode.DELETED -> {
                        // show snackbar
                    }
                    is ConfirmationCode.UPDATED -> {
                        // show snackbar
                    }
                }
            }
        }
    }
}
