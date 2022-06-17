package com.truongdc21.quickquotes.utils.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class BaseActivity <VB : ViewBinding> :
    AppCompatActivity() {
    private val binding by lazy {}
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initViews()
        initData()
    }

    abstract fun initViews()

    abstract fun initData()
}