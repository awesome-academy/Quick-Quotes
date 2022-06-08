package com.truongdc21.quickquotes.Utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast


    fun Context.showToast (string : String){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    fun Context.copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        clipboard.setPrimaryClip(clip)
    }
}
