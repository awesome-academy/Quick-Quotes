package com.truongdc21.quickquotes.presenter.activityViewPlay

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface ViewPlayActivityContract {

    interface Presenter : BasePresenter<View> {

        fun getListQuotesAPIRandom()

        fun getPositionItemQuotes(position: Int)

        fun setDataListFromItent(mList: List<Quotes>, positionJump: Int)

        fun checkItemFavorite(mList: List<Quotes>, position: Int)

        fun clickItemFavorite(mList: List<Quotes>, position: Int)

        fun insertFavorite(quotes: Quotes)

        fun removeFavorite (id: Int)
    }

    interface View {

        fun showAdapterAPIRandom(mList : List<Quotes> )

        fun showItemQuotes(mList: List<Quotes>, position: Int)

        fun showItemFromItent(mList: List<Quotes>, position: Int)

        fun checkFavorite(boolean: Boolean)

        fun onSuccess(message:  String)

        fun onError(message:  String)
    }
}
