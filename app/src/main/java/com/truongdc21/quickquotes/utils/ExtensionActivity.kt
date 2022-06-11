package com.truongdc21.quickquotes.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.AlphaAnimation

fun Activity.setStatusBar(id: Int) {
    window.statusBarColor = resources.getColor(id)
}

fun Activity.switchActivity(activity: Activity){
    startActivity(Intent(this , activity::class.java))
}
