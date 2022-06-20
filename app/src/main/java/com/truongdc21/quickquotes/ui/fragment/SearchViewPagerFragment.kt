package com.truongdc21.quickquotes.ui.fragment

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongdc21.quickquotes.presenter.SearchViewPagerFragment.SearchViewPagerFragmentContract
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.databinding.FragmentSearchViewPagerBinding
import com.truongdc21.quickquotes.ui.activity.SearchActivity
import com.truongdc21.quickquotes.ui.adapter.SearchAdapter
import com.truongdc21.quickquotes.ui.adapter.SearchAdapterVPG
import com.truongdc21.quickquotes.ui.adapter.contract.SearchAdapterContract
import com.truongdc21.quickquotes.utils.Constant
import kotlinx.android.synthetic.main.fragment_search_view_pager.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewPagerFragment:
    BaseFragment<FragmentSearchViewPagerBinding>(FragmentSearchViewPagerBinding::inflate),
    SearchViewPagerFragmentContract.ViewPageFragment,
    SearchAdapterContract.ClickItem{


    override fun initViews() {
        binding.rvSearchViewPager.layoutManager = LinearLayoutManager(this@SearchViewPagerFragment.requireContext())
    }

    override fun initData() {

    }

    override fun clickQuotes(search: Search) {

    }

    override fun clickAuthor(search: Search) {

    }

    override fun clickTag(search: Search) {

    }

    override fun clickHistory(text: String) {

    }

    override fun removeHistory(search: Search) {

    }

    override fun showAdapterQuotesSearch(mlist: List<Search>) {

    }

    override fun showAdapterAuhtorSearch(mlist: List<Search>) {

    }

    override fun showAdapterTagSearch(mlist: List<Search>) {
        lifecycleScope.launch(Dispatchers.Main){
            //adapterSearch.setDataAPI(mlist)
        }
    }

    override fun queryTextSubmitSearch(text: String) {
       // adapterSearch.filter.filter(text)
    }

    override fun queryTextChangeSearch(text: String) {
       // adapterSearch.filter.filter(text)

    }


}