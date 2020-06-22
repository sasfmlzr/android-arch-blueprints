package com.distillery.android.blueprints.mvi.todo.state

import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.flow.Flow

sealed class ToDoState {
    object LoadingState : ToDoState()
    data class DataState(val todoListFlow: Flow<List<ToDoModel>>) : ToDoState()
    data class ErrorState(val errorMsg: String) : ToDoState()
    data class SaveToDoState(val todoTask: ToDoModel) : ToDoState()
    data class DeleteState(val id: Long) : ToDoState()
    data class ConfirmationState(val id: Long) : ToDoState()
}
