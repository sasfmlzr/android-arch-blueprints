package com.distillery.android.domain

import com.distillery.android.domain.models.ToDoModel
import com.distillery.android.domain.models.isCompleted
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test
import java.util.Date

class ToDoModelTest {

    @Test
    fun `isCompleted return true if ToDoModel contain completed date`() {
        val testModel = ToDoModel(0, "", "", completedAt = Date())
        assertTrue(testModel.isCompleted)
    }

    @Test
    fun `isCompleted return false if completed date doesn't exist in ToDoModel`() {
        val testModel = ToDoModel(0, "", "")
        assertFalse(testModel.isCompleted)
    }
}
