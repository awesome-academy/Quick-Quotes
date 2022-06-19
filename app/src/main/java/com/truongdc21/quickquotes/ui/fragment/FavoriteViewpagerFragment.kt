package com.truongdc21.quickquotes.ui.fragment

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.databinding.FragmentFavoriteViewpagerBinding
import com.truongdc21.quickquotes.ui.adapter.FavoriteAdapterViewpager
import com.truongdc21.quickquotes.utils.Constant

class FavoriteViewpagerFragment:
    BaseFragment<FragmentFavoriteViewpagerBinding>(FragmentFavoriteViewpagerBinding::inflate) {

    override fun initViews() {
        binding.apply {
            vpgFavorite.adapter = FavoriteAdapterViewpager(childFragmentManager , lifecycle)
            TabLayoutMediator(tabLayoutFarvorite, vpgFavorite , object : TabLayoutMediator.TabConfigurationStrategy{
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    when (position) {
                        Constant.TAB_QUOTES -> tab.text = context?.resources?.getString(R.string.quotes)
                        Constant.TAB_AUTHOR -> tab.text = context?.resources?.getString(R.string.author)
                        Constant.TAB_TAG -> tab.text = context?.resources?.getString(R.string.tag)
                    }
                }
            }).attach()
        }
    }

    override fun initData() {}
}
