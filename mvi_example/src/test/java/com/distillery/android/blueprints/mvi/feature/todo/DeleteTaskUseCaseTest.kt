package com.distillery.android.blueprints.mvi.feature.todo

import com.distillery.android.blueprints.mvi.todo.state.TodoState
import com.distillery.android.blueprints.mvi.todo.usecases.DeleteTaskUseCase
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
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.RuntimeException
import java.net.ConnectException

class DeleteTaskUseCaseTest : KoinTest {

    @Mock
    private lateinit var mockRepo: ToDoRepository

    private val deleteTaskUseCase = DeleteTaskUseCase()

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
            Mockito.`when`(mockRepo.deleteToDo(10)).thenReturn(Unit)
            val result = deleteTaskUseCase.deleteTasks(10L)
            result.collect {
                assertTrue(it is TodoState.ConfirmationState)
            }
        }
    }

    @Test
    fun `error adding new task`() {
        runBlocking {
            BDDMockito.given(mockRepo.deleteToDo(10)).willThrow(RuntimeException("test exception", ConnectException()))
            val result = deleteTaskUseCase.deleteTasks(10L)
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
