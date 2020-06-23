package com.distillery.android.blueprints.mvp.architecture

import androidx.appcompat.app.AppCompatActivity
import org.koin.core.KoinComponent

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId), KoinComponent
