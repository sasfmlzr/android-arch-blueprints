package com.distillery.android.blueprints.mvi.feature.todo

import com.distillery.android.blueprints.mvi.todo.state.TodoState
import com.distillery.android.blueprints.mvi.todo.usecases.SaveTaskUseCase
import com.distillery.android.domain.ToDoRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SaveTaskUseCasteTest : KoinTest {

    @Mock
    private lateinit var mockRepo: ToDoRepository

    private val saveTaskUseCase = SaveTaskUseCase()

    @Before
    fun setUp() {
        startKoin {
            modules(
                    module {
                        single<ToDoRepository> { mockRepo }
                    }
            )
        }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `add succesfully new task`() {
        runBlocking {
            `when`(mockRepo.addToDo("empty", "empty")).thenReturn(Unit)
            val result = saveTaskUseCase.saveTask("empty", "empty")
            result.collect {
                assertTrue(it is TodoState.ConfirmationState)
            }
        }
    }

    @Test
    fun `error adding new task`() {
        runBlocking {
            given(mockRepo.addToDo("empty", "string")).willThrow(RuntimeException("TestException"))
            val result = saveTaskUseCase.saveTask("empty", "string")
            result.collect {
                assertTrue(it is TodoState.ErrorState)
            }
        }
    }

    @After
    fun after() {
        stopKoin()
    }
}
