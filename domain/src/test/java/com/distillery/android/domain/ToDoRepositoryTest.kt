package com.distillery.android.domain

import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ToDoRepositoryTest {

    private lateinit var repository: ToDoRepository

    @Before
    fun setup() {
        repository = FakeToDoRepository()
    }

    @Test
    fun `repository generates values on its own every 5 seconds`() = runBlocking {
        // given we subscribed to repository changes
        var listSize = 0
        val subscription = launch {
            repository.fetchToDos().collect {
                listSize = it.size
            }
        }
        // and initially list size is zero
        check(listSize == 0)
        // when 5 seconds past
        delay(5200)
        // then list has 1 element
        assertEquals(1, listSize)

        subscription.cancel()
    }

    @Test
    fun `repository add values after 1_5 seconds`() = runBlocking {
        // given we subscribed to repository changes
        var listSize = 0
        val subscription = launch {
            repository.fetchToDos().collect {
                listSize = it.size
            }
        }
        // and initially list size is zero
        check(listSize == 0)

        // when we add element
        launch {
            repository.addToDo("stub", "stub")
        }
        // and 1.7 seconds past
        delay(1700)

        // then list has 1 element
        assertEquals(1, listSize)

        subscription.cancel()
    }

    @Test
    fun `repository completes objects immediately`() = runBlocking {
        // given list has 1 element
        var listOfTodos = emptyList<ToDoModel>()
        val subscription = launch {
            repository.addToDo("stub", "stub")
            repository.fetchToDos().collect {
                listOfTodos = it
            }
        }
        delay(1700)
        check(listOfTodos.size == 1)
        // and no elements are completed
        assertTrue(listOfTodos.all { it.completedAt == null })

        // when we complete element
        repository.completeToDo(listOfTodos.first().uniqueId)

        // then list has 1 element and all elements are completed
        delay(1) // minimal delay required in order to allow suspend methods to kick in
        assertEquals(1, listOfTodos.size)
        assertTrue(listOfTodos.all { it.completedAt != null })

        subscription.cancel()
    }
}
