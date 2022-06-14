package com.truongdc21.quickquotes.presenter.fragmentHome


import com.truongdc21.quickquotes.utils.base.BasePresenter
import com.truongdc21.quickquotes.data.model.Quotes

interface HomeFragmentContact{

    interface Presenter : BasePresenter<View>{
        fun getListQuotesAPI()
        fun insertQuotesDB(quotes: Quotes)
        fun readQuotesDB()
        fun deleteQuotesDB( id: Int)
    }

    interface View {
        fun setAdapter(mListQuotes: List<Quotes>)
        fun onError()
        fun loadingWait()
        fun loadingDone()
        fun insertFavoriteSuccesss()
        fun insertFavoriteFail(exception: java.lang.Exception)
        fun setAdapterListLocal(mListQuotesLocal: List<Quotes>)
        fun deleteFavoriteSuccesss()
        fun deleteFavoriteFail(exception: java.lang.Exception)
    }
}
