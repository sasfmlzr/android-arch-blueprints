package com.distillery.android.blueprints.mvi.todo.usecases

import com.distillery.android.blueprints.mvi.todo.state.TodoState
import com.distillery.android.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.ConnectException

class DeleteTaskUseCase : KoinComponent {
    val toDoRepository: ToDoRepository by inject()

    suspend fun deleteTasks(idUnique: Long): Flow<TodoState<Unit>> {
        return flow {
            try {
                toDoRepository.deleteToDo(idUnique)
                emit(TodoState.ConfirmationState(SUCCESS_DELETION))
            } catch (connectException: ConnectException) {
                emit(TodoState.ErrorState(connectException))
            }
        }
    }

    companion object {
        const val SUCCESS_DELETION = 0L
    }
}
