package com.truongdc21.quickquotes.Utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.widget.Toast

object ToolsUI {
    fun Context.showToast (string : String){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
    fun Activity.setStatusBar(color : String){
        window.statusBarColor = Color.parseColor(color)
    }
}
