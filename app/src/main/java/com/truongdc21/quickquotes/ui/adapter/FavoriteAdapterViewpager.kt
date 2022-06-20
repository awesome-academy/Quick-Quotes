package com.truongdc21.quickquotes.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.truongdc21.quickquotes.ui.fragment.FavoriteFragment
import com.truongdc21.quickquotes.utils.Constant

class FavoriteAdapterViewpager(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return FavoriteFragment(Constant.QUOTES)
            1 -> return FavoriteFragment(Constant.AUTHOR)
            else -> return FavoriteFragment(Constant.TAG)
        }
    }
}
