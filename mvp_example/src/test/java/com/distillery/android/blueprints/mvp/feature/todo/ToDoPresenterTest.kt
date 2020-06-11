package com.distillery.android.blueprints.mvp.feature.todo

import com.distillery.android.domain.FakeToDoRepository
import com.distillery.android.domain.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ToDoPresenterTest : KoinTest {

    @Mock
    private lateinit var view: ToDoContractView

    @InjectMocks
    private lateinit var presenter: ToDoPresenter

    @Mock
    private lateinit var todoRepo: ToDoRepository

    companion object {
        private const val DELAY_TIME_FOR_COROUTINES = 6000L
    }

    private val testException = TestException()

    @Before
    fun setUp() {
        startKoin {
            modules(
                    module {
                        single<ToDoRepository> { (scope: CoroutineScope) -> FakeToDoRepository(scope) }
                    }
            )
        }

        MockitoAnnotations.initMocks(this)
        presenter = ToDoPresenter(view)
        Dispatchers.setMain(TestCoroutineDispatcher())
    }

    @Test
    fun `view received list of todo and showed it`() {
        var countItems = 0
        Mockito.doAnswer {
            countItems++
        }.`when`(view).showToDoList(ArgumentMatchers.anyList())

        runBlocking {
            presenter.fetchToDo()
            delay(DELAY_TIME_FOR_COROUTINES)
        }

        Assert.assertTrue(countItems != 0)
    }

    @Test
    fun `view throw unexpected error while receive list of todo and catch it`() {
        given(view.showToDoList(ArgumentMatchers.anyList())).willThrow(testException)
        runBlocking {
            presenter.fetchToDo()
            delay(DELAY_TIME_FOR_COROUTINES)
        }
    }

    @After
    fun after() {
        stopKoin()
    }
}
