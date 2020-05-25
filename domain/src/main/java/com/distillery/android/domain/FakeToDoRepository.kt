package com.distillery.android.domain

import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

private val uniqueIdGenerator = AtomicLong(0)

private const val DEFAULT_DELAY_GENERATOR = 5000L
private const val DEFAULT_DELAY_ADDITION = 1500L
private const val TIME_TO_DIE = 10L

class FakeToDoRepository : ToDoRepository {

    private val toDos = ConcurrentHashMap<Long, ToDoModel>()
    private val todosChannel = Channel<List<ToDoModel>>()

    init {
        GlobalScope.launch {
            generateValues().collect { model ->
                toDos[model.uniqueId] = model
                println("${ToDoRepository::class.simpleName} Publishing value ${model.uniqueId}")
                todosChannel.send(toDos.values.toList())
            }
        }
    }

    override fun fetchToDos(): Flow<List<ToDoModel>> {
        return todosChannel.receiveAsFlow()
    }

    override suspend fun addToDo(title: String, description: String) {
        println("${ToDoRepository::class.simpleName} Started value addition")
        delay(DEFAULT_DELAY_ADDITION)
        val id = uniqueIdGenerator.getAndIncrement()
        val model = ToDoModel(id, "$title ID: $id", description)
        toDos[model.uniqueId] = model
        println("${ToDoRepository::class.simpleName} Value ${model.uniqueId} added")
        if (uniqueIdGenerator.get() == TIME_TO_DIE) {
            // moment of surprise
            println("${ToDoRepository::class.simpleName} Oops")
            throw IllegalArgumentException("You died")
        } else {
            println("${ToDoRepository::class.simpleName} Publishing value ${model.uniqueId}")
            todosChannel.send(toDos.values.toList())
        }
    }

    override fun completeToDo(uniqueId: Long) {
        if (uniqueId > 2 && uniqueId % 2 == 0L) {
            // moment of surprise
            throw IllegalArgumentException("You died")
        }
        val model = toDos[uniqueId]?.copy(completedAt = Date())
            ?: return
        toDos[model.uniqueId] = model
        GlobalScope.launch {
            todosChannel.send(toDos.values.toList())
        }
    }

    private fun generateValues() = flow {
        println("${ToDoRepository::class.simpleName} Started new value generation")
        delay(DEFAULT_DELAY_GENERATOR)
        val model = createToDo(uniqueIdGenerator.getAndIncrement())
        println("${ToDoRepository::class.simpleName} Value generated")
        if (uniqueIdGenerator.get() == TIME_TO_DIE) {
            println("${ToDoRepository::class.simpleName} Oops")
            // moment of surprise
            todosChannel.close(IllegalArgumentException("You died"))
        } else {
            println("${ToDoRepository::class.simpleName} Emitting value ${model.uniqueId}")
            emit(model)
        }
    }

    private fun createToDo(id: Long) = ToDoModel(
        uniqueId = id,
        title = "ToDo №$id",
        description = "Awesome stuff to do!"
    )
}
