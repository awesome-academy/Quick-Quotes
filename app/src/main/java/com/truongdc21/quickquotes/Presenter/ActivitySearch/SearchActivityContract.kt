package com.truongdc21.quickquotes.presenter.activitySearch

import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface SearchActivityContract {
    interface Presenter: BasePresenter<View> {

        fun getListSearchAPIQuotes()

        fun getListSearchAPIAuthor()

        fun getListSearchAPITag()

        fun setPositionItemQuotes(position: Int)

    }

    interface View {

        fun sendSearchQuotestoFragment(mList : List<Search>)

        fun sendSearchAuthorFragment(mList: List<Search>)

        fun sendSearchTagFragment(mList: List<Search>)

        fun loadingPropressbar()

        fun loadingSuccsess()

    }
}
