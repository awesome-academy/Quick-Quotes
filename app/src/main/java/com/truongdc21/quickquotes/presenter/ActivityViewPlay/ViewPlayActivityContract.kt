package com.truongdc21.quickquotes.presenter.activityViewPlay


import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface ViewPlayActivityContract {

    interface Presenter : BasePresenter<View> {

        fun getListQuotesAPIRandom()

        fun getPositionItemQuotes(position: Int)

        fun getListQuotesApiReload()

        fun setDataListFromItent(mList: List<Quotes>, positionJump: Int)

        fun checkItemFavorite(mList: List<Quotes>, position: Int)

        fun clickItemFavorite(mList: List<Quotes>, position: Int)

        fun insertFavorite(quotes: Quotes)

        fun removeFavorite (id: Int)

        fun translateQuotes(position: Int)

        fun getDataAuthor()

        fun getDataTag()

        fun checkFavoriteAuthor(srtAuthor: String)

        fun checkFavoriteTag(srtTag: String)

        fun insertAuthor(srtAuthor: String)

        fun deleteAuthor(id: Int)

        fun insertTag(srtTag: String)

        fun deleteTag(id: Int)

        fun removeItemQuotes(position: Int)

        fun checkItemFavoriteAuthotBottomSheet(srtAuthor: String): Boolean

        fun checkItemFavoriteTagBottomSheet(srtTag: String): Boolean
    }

    interface View {

        fun showAdapterAPIRandom(mList : List<Quotes> )

        fun showItemQuotes(mList: List<Quotes>, position: Int)

        fun showItemFromItent(mList: List<Quotes>, position: Int)

        fun showItemFromApiReload( position: Int)

        fun checkFavorite(boolean: Boolean)

        fun translateQuotes (boolean: Boolean , srtQuotes: String)

        fun showAdapterLaterRemove(mList: List<Quotes>)

        fun loadingPropressbar()

        fun loadingSuccess()

        fun onSuccess(message:  String)

        fun onError(message:  String)
    }
}
