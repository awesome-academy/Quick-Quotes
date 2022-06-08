package com.truongdc21.quickquotes.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent

object ExtensionActivity {

    fun Activity.setStatusBar(id : Int) {
        window.statusBarColor = resources.getColor(id)
    }

    fun Activity.switchActivity (activity: Activity){
        startActivity(Intent(this , activity::class.java))
    }

    fun  Activity.saveSharedPreferences ( key : String, value : String) {
        val SharePref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = SharePref.edit()
        editor.putString(key , value).apply()
    }
}
