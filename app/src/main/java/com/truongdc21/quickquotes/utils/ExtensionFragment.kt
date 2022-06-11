package com.truongdc21.quickquotes.utils

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.truongdc21.quickquotes.R

fun FragmentActivity.replaceFragmentBottomNAV(fragment : Fragment){
    this.supportFragmentManager.beginTransaction().apply {
        replace(R.id.mainContainer , fragment)
        commit()
    }
}

