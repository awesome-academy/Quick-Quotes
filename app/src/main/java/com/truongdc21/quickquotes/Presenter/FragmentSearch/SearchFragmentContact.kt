package com.truongdc21.quickquotes.presenter.fragmentSearch

import android.view.View
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface SearchFragmentContact {

    interface Presenter: BasePresenter<View> {

        fun getListSearchAPI()

        fun getListSearchHistory()

        fun insertSearchHistory(search: Search)

        fun deleteSearchHistory(id : Int)
    }

    interface View {

<<<<<<< HEAD
        fun setAdapterListAPI(mListSearch : List<Search>)

        fun setAdapterListHistory(mListSearch: List<Search>)
=======
        fun showAdapterListAPI(mListSearch : List<Search>)

        fun showAdapterListHistory(mListSearch: List<Search>)
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data

        fun removeHistorySuccess()

        fun onError()
    }
}
