package com.distillery.android.blueprints.mvi.todo.usecases

import com.distillery.android.blueprints.mvi.todo.TodoListModel
import com.distillery.android.blueprints.mvi.todo.state.ConfirmationCode
import com.distillery.android.blueprints.mvi.todo.state.TodoState
import com.distillery.android.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.ConnectException

class SaveTaskUseCase : KoinComponent {
    private val toDoRepoRepository: ToDoRepository by inject()
    suspend fun saveTask(title: String, description: String): Flow<TodoState<TodoListModel>> {
        return flow {
            try {
                toDoRepoRepository.addToDo(title, description)
                toDoRepoRepository.fetchToDos().map {
                    // TODO: filter list TODO MODEL
                    emit(TodoState.DataState(TodoListModel(it, it)))
                }
                emit(TodoState.ConfirmationState(ConfirmationCode.SAVED))
            } catch (connectionException: ConnectException) {
                emit(TodoState.ErrorState(connectionException))
            }
        }
    }
}
