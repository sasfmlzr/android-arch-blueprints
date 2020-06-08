package com.distillery.android.domain

import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.flow.Flow
import java.net.ConnectException

interface ToDoRepository {
    /**
     * Fetches news To Do items.
     * New item appears from time to time.
     * @throws IllegalArgumentException when flow is closed due to inner error
     */
    fun fetchToDos(): Flow<List<ToDoModel>>

    /**
     * Creates a new To Do action asynchronously and adds it to the existing list of To Dos.
     * @throws ConnectException when new value creation failed
     */
    suspend fun addToDo(title: String, description: String)

    /**
     * Mark To Do with [uniqueId] as completed synchronously.
     * @throws UnsupportedOperationException when complete To Do with even id
     */
    fun completeToDo(uniqueId: Long)

    /**
     * Deletes a To Do action asynchronously and deletes it from the existing list of To Dos.
     */
    suspend fun deleteToDo(uniqueId: Long)
}
