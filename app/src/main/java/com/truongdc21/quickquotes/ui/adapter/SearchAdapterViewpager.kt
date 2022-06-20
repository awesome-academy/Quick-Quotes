package com.truongdc21.quickquotes.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.truongdc21.quickquotes.ui.fragment.SearchViewPagerFragment
import com.truongdc21.quickquotes.utils.Constant

class SearchAdapterViewpager(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager, lifecycle){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            Constant.TAB_QUOTES -> return SearchViewPagerFragment()
            Constant.TAB_AUTHOR -> return SearchViewPagerFragment()
            else -> return SearchViewPagerFragment()
        }
    }
}
