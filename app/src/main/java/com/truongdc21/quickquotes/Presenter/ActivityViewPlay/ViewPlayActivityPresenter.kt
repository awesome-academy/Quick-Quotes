package com.truongdc21.quickquotes.presenter.activityViewPlay

import android.content.Context
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import java.lang.Exception

class ViewPlayActivityPresenter(
    private val mContext: Context,
    private val mRepoQuotes : QuotesRepository
    ):ViewPlayActivityContract.Presenter{

    private var mView : ViewPlayActivityContract.View? = null
    private var mListQuotes = mutableListOf<Quotes>()
    private var mPosition : Int? = null

    override fun onStart() {}

    override fun onStop() {}

    override fun setView(view: ViewPlayActivityContract.View?) {
       this.mView = view
    }

    override fun getListQuotesAPIRandom() {
        mRepoQuotes.getApiQuotesList(object : OnRemoteResultListener<List<Quotes>>{
            override fun onSuccess(data: List<Quotes>) {
                mView?.showAdapterAPIRandom(data)
                mListQuotes.apply {
                    clear()
                    addAll(data)
                }
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun getPositionItemQuotes(position: Int) {
        mPosition = position
        mView?.showItemQuotes(mListQuotes , position )
    }

    override fun setDataListFromItent(mList: List<Quotes>, position: Int) {
        this.mListQuotes.apply {
            clear()
            addAll(mList)
        }
        mPosition = position
        mView?.showItemFromItent(mList , position)
    }

    override fun checkItemFavorite(mList: List<Quotes>, position: Int) {
        mRepoQuotes.readQuotes(object : OnLocalResultListener<List<Quotes>>{
            override fun onSuccess(data: List<Quotes>) {
                for (i in data){
                    if (mList[position].mQuotes == i.mQuotes){
                        mView?.checkFavorite(true)
                        break
                    }else {
                        mView?.checkFavorite(false)
                    }
                }
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun clickItemFavorite(mList: List<Quotes>, position: Int) {
        mRepoQuotes.readQuotes(object : OnLocalResultListener<List<Quotes>>{
            private val itemQuotes = mListQuotes[position]
            override fun onSuccess(data: List<Quotes>) {
                for (i in data){
                    if(itemQuotes.mQuotes == i.mQuotes){
                        removeFavorite(i.id)
                        checkItemFavorite(mList, position)
                        return
                    }
                }
                insertFavorite(itemQuotes)
                checkItemFavorite(mList, position)
            }
            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun insertFavorite(quotes: Quotes) {
        mRepoQuotes.insertQuotes(quotes, object : OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.addFavoriteSuccesss))
            }
            override fun onError(exception: Exception?) {
                mView?.onSuccess(mContext.resources.getString(R.string.addFavoriteFail))
            }
        })
    }

    override fun removeFavorite(id: Int) {
        mRepoQuotes.deleteQuotes(id, object : OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.remove_success))
            }
            override fun onError(exception: Exception?) {
                mView?.onSuccess(mContext.resources.getString(R.string.remove_error))
            }
        })
    }
}
