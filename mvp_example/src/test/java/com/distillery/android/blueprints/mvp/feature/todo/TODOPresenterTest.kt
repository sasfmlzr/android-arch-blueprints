package com.distillery.android.blueprints.mvp.feature.todo

import com.distillery.android.domain.FakeToDoRepository
import com.distillery.android.domain.ToDoRepository
import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
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
import org.mockito.BDDMockito.isA
import org.mockito.InjectMocks
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class TODOPresenterTest : KoinTest {

    @Mock
    private lateinit var view: TODOContractView

    @InjectMocks
    private lateinit var presenter: TODOPresenter

    @Mock
    private lateinit var todoRepo: ToDoRepository

    private val scope = TestCoroutineScope()
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
        presenter = TODOPresenter(view)
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
            delay(10000)
        }

        Assert.assertTrue(countItems != 0)
    }

    @Test
    fun `view throw unexpected error while receive list of todo and catch it`() {
        given(view.showToDoList(ArgumentMatchers.anyList())).willThrow(testException)
        runBlocking {
            presenter.fetchToDo()
            delay(15000)
        }
    }

    @After
    fun after() {
        stopKoin()
    }
}
