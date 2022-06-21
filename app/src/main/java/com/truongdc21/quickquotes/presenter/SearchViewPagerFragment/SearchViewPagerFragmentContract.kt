package com.truongdc21.quickquotes.presenter.SearchViewPagerFragment

import com.truongdc21.quickquotes.data.model.Search

interface SearchViewPagerFragmentContract {


    interface ViewPageFragment{

        fun showAdapterQuotesSearch(mlist : List<Search>)

        fun showAdapterAuhtorSearch (mlist: List<Search>)

        fun showAdapterTagSearch(mlist: List<Search>)

        fun queryTextSubmitSearch(text : String)

        fun queryTextChangeSearch(text: String)
    }

}