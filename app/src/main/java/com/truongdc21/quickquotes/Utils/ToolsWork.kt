package com.truongdc21.quickquotes.Utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import java.security.Key

object ToolsWork {

    fun  AppCompatActivity.saveSharedPreferences ( key : String, value : String) {
        val SharePref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = SharePref.edit()
        editor.putString(key , value).apply()
    }
}
