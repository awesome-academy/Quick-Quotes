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
<<<<<<< HEAD
<<<<<<< HEAD
        
=======

>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
=======

>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
        fun showAdapterQuotes(mlIst: List<Quotes>)

        fun showAdapterAuthor(mList: List<Author>)

        fun showAdapterTag(mList : List<Tag>)

        fun removeSuccess (message: String)

        fun onError(message : String)
    }
}
