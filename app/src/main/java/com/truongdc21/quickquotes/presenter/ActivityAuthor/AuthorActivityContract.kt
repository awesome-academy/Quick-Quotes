package com.truongdc21.quickquotes.presenter.activityAuthor

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface AuthorActivityContract {

    interface Persenter : BasePresenter<View> {

        fun getListAuthorFromAPI(key : String)

        fun insertQuotesDB(quotes: Quotes)

        fun readQuotesDB()

        fun deleteQuotesDB( quotes: Quotes , mlistLocal : List<Quotes>)

        fun getListAuthor()

        fun getListTag()

        fun checkFavoriteAuthor(srtAuthor: String)

        fun checkFavoriteTag(srtTag : String)

        fun insertAuthor(srtAuthor: String)

        fun deleteAuthor(id: Int)

        fun insertTag(srtTag: String)

        fun deleteTag(id : Int)
    }

    interface View : AuthorActivityContract {

        fun showAdapterAuthor(mList: List<Quotes>)

        fun showAdapterListLocal(mList: List<Quotes>)

        fun onSuccess(message: String)

        fun onError(message: String)

        fun setDataAuthor(mList: List<Author>)

        fun setDataTag(mList: List<Tag>)

        fun loadingPropressbar()

        fun loadingSuccess()
    }
}
