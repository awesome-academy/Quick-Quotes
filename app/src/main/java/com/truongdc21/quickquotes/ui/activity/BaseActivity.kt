package com.truongdc21.quickquotes.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> (
        val inflate : (LayoutInflater) -> VB
    ): AppCompatActivity() {

    val binding : VB by lazy { inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        initData()
    }

    abstract fun initViews()

    abstract fun initData()
}
