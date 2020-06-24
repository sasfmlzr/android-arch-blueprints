package com.distillery.android.blueprints.mvi.todo.usecases

import com.distillery.android.blueprints.mvi.todo.state.ToDoState
import com.distillery.android.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.net.ConnectException

class SaveTaskUseCase : KoinComponent {
    private val toDoRepo: ToDoRepository by inject()
    suspend fun saveTask(title: String, description: String): Flow<ToDoState<Unit>> {
        return flow {
            try {
                toDoRepo.addToDo(title, description)
                emit(ToDoState.ConfirmationState(SUCCESS))
            } catch (connectionException: ConnectException) {
                emit(ToDoState.ErrorState(connectionException))
            }
        }
    }

    companion object {
        const val SUCCESS = 1L
    }
}
