package com.truongdc21.quickquotes.Utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.truongdc21.quickquotes.R

object ExtensionFragment {

    fun FragmentActivity.replaceFragment (fragment : Fragment){
        this.supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_main , fragment)
            commit()
        }
    }
}
