package com.distillery.android.blueprints.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.distillery.android.domain.ToDoRepository
import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    // collects the flow and convert it to livedata with view model scope
    private val allTodoListLiveData: LiveData<List<ToDoModel>> =
            toDoRepository.fetchToDos()
                    .asLiveData(viewModelScope.coroutineContext)

    val todoListLiveData = allTodoListLiveData.map {
        it.filter { item -> item.completedAt == null } // non-completedTodo list
    }

    val completedTodoListLiveData = allTodoListLiveData.map {
        it.filter { item -> item.completedAt != null } // completedTodo list
    }

    /**
     * calls the repo to make the item completed
     */
    fun completeTodo(toDoModel: ToDoModel) {
        toDoRepository.completeToDo(toDoModel.uniqueId)
    }

    /**
     * launch coroutine and calls the repo to make the item deleted
     */
    fun deleteTodo(toDoModel: ToDoModel) {
        viewModelScope.launch { toDoRepository.deleteToDo(toDoModel.uniqueId) }
    }

    /**
     * launch coroutine and calls the repo to make the item added
     */
    fun addTodo(title: String, description: String) {
        viewModelScope.launch { toDoRepository.addToDo(title, description) }
    }
}
