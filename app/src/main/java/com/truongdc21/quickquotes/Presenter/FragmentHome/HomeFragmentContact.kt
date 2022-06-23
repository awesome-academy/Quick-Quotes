package com.truongdc21.quickquotes.presenter.fragmentHome


import android.webkit.ConsoleMessage
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.utils.base.BasePresenter
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag

interface HomeFragmentContact{

    interface Presenter : BasePresenter<View>{

        fun getListQuotesAPI()

        fun insertQuotesDB(quotes: Quotes)

        fun readQuotesDB()

        fun deleteQuotesDB( id: Int)

        fun getListAuthor()

        fun getListTag()

        fun checkFavoriteAuthor(srtAuthor: String)

        fun checkFavoriteTag(srtTag: String)

        fun insertAuthor(srtAuthor: String)

        fun insertTag(srtTag: String)

        fun deleteAuthor(id : Int)

        fun deleteTag(id: Int)
    }

    interface View {

        fun setAdapter(mListQuotes: List<Quotes>)

        fun loadingWait()

        fun loadingDone()

        fun showAdapterListLocal(mListQuotesLocal: List<Quotes>)

        fun deleteFavoriteSuccesss()

        fun deleteFavoriteFail(exception: java.lang.Exception)

        fun setDataAuthor(mList: List<Author> )

        fun setDataTag(mList: List<Tag> )

        fun showToast(message: String)
    }
}
