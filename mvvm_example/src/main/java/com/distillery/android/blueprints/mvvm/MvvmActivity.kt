package com.distillery.android.blueprints.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.distillery.android.mvvm_example.R

class MvvmActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvvm_activity)
    }
}
