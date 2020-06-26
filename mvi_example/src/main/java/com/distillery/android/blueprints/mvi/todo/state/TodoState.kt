package com.distillery.android.blueprints.mvi.todo.state

sealed class TodoState<out T> {
    object LoadingState : TodoState<Nothing>()
    data class DataState<out T> (val todoListFlow: T) : TodoState<T>()
    data class ErrorState(val errorMsg: Throwable?) : TodoState<Nothing>()
    data class SaveTodoState<out T>(val title: String, val description: String) : TodoState<T>()
    data class DeleteState(val id: Long) : TodoState<Nothing>()
    data class ConfirmationState(val id: Long) : TodoState<Nothing>()
}
