package com.truongdc21.quickquotes.utils

import android.app.Activity
import android.content.Intent

fun Activity.setStatusBar(id: Int) {
    window.statusBarColor = resources.getColor(id)
}

fun Activity.switchActivity (activity: Activity){
    startActivity(Intent(this , activity::class.java))
}
