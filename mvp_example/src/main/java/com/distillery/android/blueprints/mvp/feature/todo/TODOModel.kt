package com.distillery.android.blueprints.mvp.feature.todo

import android.os.Parcelable
import com.distillery.android.domain.models.ToDoModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TODOModel(val toDoList: List<ToDoModel>) : Parcelable
