package com.truongdc21.quickquotes.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.ui.activity.AuthorActivity
import com.truongdc21.quickquotes.ui.activity.SearchActivity
import com.truongdc21.quickquotes.ui.activity.TagActivity
import com.truongdc21.quickquotes.ui.activity.ViewPlayActivity
import java.security.Key

fun Context.showToast(string: String){
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

fun Context.copyToClipboard(text: CharSequence) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("label",text)
    clipboard.setPrimaryClip(clip)
    this.showToast(resources.getString(R.string.copy))
}

fun Context.switchActivity(activity: Activity){
    startActivity(Intent(this , activity::class.java))
}

fun Context.switchAuthorActivity(srtAuthor : String){
    val intent = Intent(this , AuthorActivity::class.java)
    intent.putExtra(Constant.INTEN_AUTHOR, srtAuthor)
    startActivity(intent)
}

fun Context.switchTagActivity(srtTag : String){
    val intent = Intent(this , TagActivity::class.java)
    intent.putExtra(Constant.INTEN_TAG, srtTag)
    startActivity(intent)
}
fun Context.switchViewPlayActivity(mList : List<Quotes> , position : Int){
    val intent = Intent(this , ViewPlayActivity::class.java)
    val bundle = Bundle()
    bundle.putParcelableArrayList(Constant.INTENT_VIEWPLAY_QUOTES , mList as ArrayList)
    bundle.putInt(Constant.INTENT_VIEWPLAY_POSITION , position)
    intent.putExtras(bundle)
    startActivity(intent)
}

fun Context.sharedText(title: String, value: String){
    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_TEXT, value)
    intent.type = "text/plain"
    this.startActivity(Intent.createChooser(intent, title))
}

fun Context.switchSearchActivity(text: String){
    val intent = Intent(this, SearchActivity::class.java)
    intent.putExtra(Constant.PUT_STRING_SEARCH ,text )
    startActivity(intent)
}

fun Context?.isNetworkAvailable(): Boolean {
    if (this == null) return false
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}
