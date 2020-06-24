package com.distillery.android.blueprints.mvi.todo.usecases

import com.distillery.android.blueprints.mvi.todo.state.TodoState
import com.distillery.android.domain.ToDoRepository
import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetToDoListUseCase : KoinComponent {
    private val toDoRepo: ToDoRepository by inject()

    suspend fun getToDoList(): Flow<TodoState<List<ToDoModel>>> {
        return flow {
            toDoRepo.fetchToDos().catch { e ->
                emit(TodoState.ErrorState(e.cause))
            }.map { emit(TodoState.DataState(it)) }
        }
    }
}
