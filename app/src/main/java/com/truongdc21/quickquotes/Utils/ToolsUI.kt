package com.truongdc21.quickquotes.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.truongdc21.quickquotes.R

object ToolsUI {

    fun Context.showToast (string : String){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
    fun Activity.setStatusBar(id : Int) {
        window.statusBarColor = resources.getColor(id)
    }

    fun FragmentActivity.replaceFragment (fragment : Fragment){
        this.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_main , fragment)
            commit()
        }
    }

    fun Activity.switchActivity (activity: Activity){
        startActivity(Intent(this , activity::class.java))
    }


}
