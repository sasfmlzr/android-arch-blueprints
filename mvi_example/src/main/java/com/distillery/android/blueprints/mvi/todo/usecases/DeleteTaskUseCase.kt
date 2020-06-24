package com.distillery.android.blueprints.mvi.todo.usecases

import com.distillery.android.blueprints.mvi.todo.state.ToDoState
import com.distillery.android.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.ConnectException

class DeleteTaskUseCase : KoinComponent {
    val toDoRepo: ToDoRepository by inject()

    suspend fun deleteTasks(idUnique: Long): Flow<ToDoState<Unit>> {
        return flow {
            try {
                toDoRepo.deleteToDo(idUnique)
                emit(ToDoState.ConfirmationState(SUCCESS_DELETION))
            } catch (connectException: ConnectException) {
                emit(ToDoState.ErrorState(connectException))
            }
        }
    }

    companion object {
        const val SUCCESS_DELETION = 0L
    }
}
