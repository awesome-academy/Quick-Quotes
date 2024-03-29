package com.truongdc21.quickquotes.presenter.fragmentSearch

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface SearchFragmentContact {

    interface Presenter: BasePresenter<View> {

        fun getListSearchAPI()

        fun getListSearchHistory()

        fun insertSearchHistory(search: Search)

        fun deleteSearchHistory(id : Int)

        fun clickQuotesSearch(text : String)

    }

    interface View {

        fun showAdapterListAPI(mListSearch : List<Search>)

        fun showAdapterListHistory(mListSearch: List<Search>)

        fun switchActivityViewPlay(mList : List<Quotes>)

        fun loadingApi()

        fun loadingApiSuccess()

        fun removeHistorySuccess()

        fun onError()

        fun showToast(message: String)
    }
}
