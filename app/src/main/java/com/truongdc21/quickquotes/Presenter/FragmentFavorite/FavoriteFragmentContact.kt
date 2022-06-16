package com.truongdc21.quickquotes.presenter.fragmentFavorite

import android.view.View
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface FavoriteFragmentContact {

    interface Presenter: BasePresenter<View> {

        fun getListQuotesFavorite()

        fun getListAuthorFavorite()

        fun getListTagFavorite()

        fun removeQuotes(id: Int)

        fun removeAuthor(id: Int)

        fun removeTag(id: Int)

    }

    interface View {

        fun setAdapterQuotes(mlIst: List<Quotes>)

        fun setAdapterAuthor(mList: List<Author>)

        fun setAdapterTag(mList : List<Tag>)

        fun removeSuccess (message: String)

        fun onError(message : String)
    }
}
