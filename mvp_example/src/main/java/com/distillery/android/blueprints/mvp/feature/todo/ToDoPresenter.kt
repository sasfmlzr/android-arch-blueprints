package com.distillery.android.blueprints.mvp.feature.todo

import com.distillery.android.blueprints.mvp.architecture.BasePresenter
import com.distillery.android.domain.ToDoRepository
import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class ToDoPresenter(override var view: ToDoView) : BasePresenter<ToDoView>(view), CoroutineScope {

    private val job = Job()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        launch(Dispatchers.Main + Job()) {
            view.showError(throwable.message!!)
        }
    }
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO + coroutineExceptionHandler

    private val todoRepo: ToDoRepository by inject { parametersOf(this) }

    private fun finishLoading() {
        view.endLoading()
    }

    fun changeView(view: ToDoView) {
        this.view = view
    }

    fun fetchToDo() {
        launch {
            view.startLoading()
            todoRepo.fetchToDos().catch {
                        withContext(Dispatchers.Main) {
                            view.showError(it.toString())
                        }
                    }
                    .collect {
                        withContext(Dispatchers.Main) {
                            view.showToDoList(it)
                            finishLoading()
                        }
                    }
        }
    }

    fun addToDo() {
        view.addToDo { title, description ->
            launch {
                todoRepo.addToDo(title, description)
            }
        }
    }

    fun deleteToDo(uniqueId: Long) {
        launch {
            todoRepo.deleteToDo(uniqueId)
        }
    }

    fun completeToDo(toDoModel: ToDoModel) {
        try {
            todoRepo.completeToDo(toDoModel.uniqueId)
        } catch (e: UnsupportedOperationException) {
            view.showError(e.message!!)
        }
    }

    /**
     * Use it for cancellation ToDoRepository tasks when you route to another fragment.
     */
    fun cleanup() {
        job.cancel()
    }
}
