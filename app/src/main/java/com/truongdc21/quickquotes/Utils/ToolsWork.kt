package com.truongdc21.quickquotes.Utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

object ToolsWork {

    fun  AppCompatActivity.saveSharedPreferences ( key : String, value : String) {
        val SharePref = this.getPreferences(Context.MODE_PRIVATE)
        val editor = SharePref.edit()
        editor.putString(key , value).apply()
    }

    fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this , "Coppy to clipboard", Toast.LENGTH_SHORT).show()
    }
}
