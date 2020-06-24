package com.distillery.android.blueprints.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.distillery.android.domain.ToDoRepository
import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.launch

class TodoViewModel(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    // collects the flow and convert it to livedata with view model scope
    private val allTodoListLD: LiveData<List<ToDoModel>> =
            toDoRepository.fetchToDos()
                    .asLiveData(viewModelScope.coroutineContext)

    val todoListLD = allTodoListLD.map {
        it.filter { item -> item.completedAt == null } // non-completedTodo list
    }

    val completedTodoListLD = allTodoListLD.map {
        it.filter { item -> item.completedAt != null } // completedTodo list
    }

    /**
     * calls the repo to make the item completed
     */
    fun completeTodo(id: Long) {
        toDoRepository.completeToDo(id)
    }

    /**
     * launch coroutine and calls the repo to make the item deleted
     */
    fun deleteTodo(id: Long) {
        viewModelScope.launch { toDoRepository.deleteToDo(id) }
    }

    /**
     * launch coroutine and calls the repo to make the item added
     */
    fun addTodo(title: String, description: String) {
        viewModelScope.launch { toDoRepository.addToDo(title, description) }
    }
}
