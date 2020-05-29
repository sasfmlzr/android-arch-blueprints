package com.distillery.android.blueprints.mvp.feature.todo

import com.distillery.android.blueprints.mvp.architecture.BasePresenter
import com.distillery.android.domain.ToDoRepository
import com.distillery.android.domain.models.ToDoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.inject
import java.util.Date

class TODOPresenter(private val view: TODOContractView?) : BasePresenter(view) {

    private val todoRepo: ToDoRepository by inject()

    init {
        if (view == null) {
            throw NullPointerException("View should be implemented")
        }
    }

    companion object {
        private const val DELAY_FOR_MOCK_DATA = 5000L
        private const val SIZE_LIST = 10
    }

    fun getToDoList() {
        val list = generateSequence(ToDoModel(0, "Title", "Description", Date(), Date())) {
            it.copy(it.uniqueId + 1)
        }.take(SIZE_LIST).toList()

        view?.startLoading()

        GlobalScope.launch(Dispatchers.IO) {
            delay(DELAY_FOR_MOCK_DATA)
            withContext(Dispatchers.Main) {
                view?.showToDoList(list)
                finishLoading()
            }
        }
    }

    private fun finishLoading() {
        view?.endLoading()
    }
}
