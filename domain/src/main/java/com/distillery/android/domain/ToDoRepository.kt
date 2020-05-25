package com.distillery.android.domain

import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    /**
     * Fetches news To Do items.
     * New item appears from time to time.
     */
    fun fetchToDos(): Flow<List<ToDoModel>>

    /**
     * Creates a new To Do action asynchronously and adds it to the existing list of To Dos.
     */
    suspend fun addToDo(title: String, description: String)

    /**
     * Mark To Do with [uniqueId] as completed synchronously.
     */
    fun completeToDo(uniqueId: Long)
}
