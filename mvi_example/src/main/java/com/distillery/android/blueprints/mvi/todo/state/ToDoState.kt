package com.distillery.android.blueprints.mvi.todo.state

sealed class ToDoState<out T> {
    object LoadingState : ToDoState<Nothing>()
    data class DataState<out T> (val todoListFlow: T) : ToDoState<T>()
    data class ErrorState (val errorMsg: Throwable?) : ToDoState<Nothing>()
    data class SaveToDoState<out T>(val title: String, val description: String) : ToDoState<T>()
    data class DeleteState(val id: Long) : ToDoState<Unit>()
    data class ConfirmationState(val id: Int) : ToDoState<Unit>()
}
