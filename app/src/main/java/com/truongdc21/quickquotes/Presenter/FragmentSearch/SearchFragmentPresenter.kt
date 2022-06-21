package com.truongdc21.quickquotes.presenter.fragmentSearch

import android.content.Context
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.repository.SearchRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.isNetworkAvailable
import kotlinx.coroutines.*
import java.lang.Exception

class SearchFragmentPresenter(
    private val mContext: Context,
    private val mRepo : SearchRepository
    ) : SearchFragmentContact.Presenter{

    private var mView : SearchFragmentContact.View? = null
    private var mlistSearch = mutableListOf<Search>()
    private var mlistQuotes = mutableListOf<Quotes>()
    private var isQuotes : Boolean = false
    private var isAuthor : Boolean = false
    private var isTag : Boolean = false

    override fun onStart() {
        getListSearchAPI()
        getListSearchHistory()
    }

    override fun onStop() {
        getListSearchAPI()
    }

    override fun setView(view: SearchFragmentContact.View?) {
        this.mView = view
    }

    override fun getListSearchAPI() {
        if (!mContext.isNetworkAvailable()){
            mView?.showToast(mContext.resources.getString(R.string.internet_disconnect))
            return
        }
        if (isQuotes || isAuthor || isTag) {
            mView?.showAdapterListAPI(mlistSearch)
        } else {
            CoroutineScope(Dispatchers.IO).launch{
                launch {
                    mRepo.getListQuotes(object : OnRemoteResultListener<List<Quotes>>{
                        override fun onSuccess(data: List<Quotes>) {
                            for (i in data){
                                mlistSearch.add(Search(0 , ConstanceDb.COLUMN_QUOTES , i.mQuotes , Constant.REMOTE))
                            }
                            mView?.showAdapterListAPI(mlistSearch)
                            mlistQuotes.apply {
                                clear()
                                addAll(data)
                            }
                            isQuotes = true
                        }
                        override fun onError(exception: Exception?) { mView?.onError() }
                    })
                }

                launch {
                    mRepo.getListAuthor(object : OnRemoteResultListener<List<String>> {
                        override fun onSuccess(data: List<String>) {
                            for (author in data){
                                mlistSearch.add(Search(0, ConstanceDb.COLUMN_AUTHOR, author, Constant.REMOTE))
                            }
                            mView?.showAdapterListAPI(mlistSearch)
                            isAuthor = true
                        }
                        override fun onError(exception: Exception?) { mView?.onError() }
                    })
                }

                launch {
                    mRepo.getListTag(object : OnRemoteResultListener<List<String>> {
                        override fun onSuccess(data: List<String>) {
                            for (tag in data){
                                mlistSearch.add(Search(0, ConstanceDb.COLUMN_TAG, tag, Constant.REMOTE))
                            }
                            mView?.showAdapterListAPI(mlistSearch)
                            isTag = true
                        }
                        override fun onError(exception: Exception?) { mView?.onError() }
                    })
                }
            }
        }
    }

    override fun getListSearchHistory() {
       mRepo.readSearch(object : OnLocalResultListener<List<Search>>{
           override fun onSuccess(data: List<Search>) {
               mView?.showAdapterListHistory(data)
           }
           override fun onError(exception: Exception?) {}
       })
    }

    override fun insertSearchHistory(search: Search) {
        mRepo.readSearch(object : OnLocalResultListener<List<Search>>{
            override fun onSuccess(data: List<Search>) {
                for (i in data){
                    if (i.text == search.text){
                        i.id?.let {
                            mRepo.deleteSearch(it , object: OnLocalResultListener<Unit>{
                                override fun onSuccess(data: Unit) { getListSearchHistory() }
                                override fun onError(exception: Exception?) {}
                            })
                        }
                    }
                }
                mRepo.insertSearch(search , object : OnLocalResultListener<Unit>{
                    override fun onSuccess(data: Unit) {}
                    override fun onError(exception: Exception?) {}
                })
            }
            override fun onError(exception: Exception?) {}
        })
    }

    override fun deleteSearchHistory(id : Int) {
        mRepo.deleteSearch(id , object: OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                getListSearchHistory()
                mView?.removeHistorySuccess()
            }
            override fun onError(exception: Exception?) {}
        })
    }

    override fun clickQuotesSearch(text: String) {
        if (!mContext.isNetworkAvailable()){
            mView?.showToast(mContext.resources.getString(R.string.internet_disconnect))
            return
        }
        mView?.loadingApi()
        mRepo.getListQuotes(object : OnRemoteResultListener<List<Quotes>>{
            var mlistQuotesOneItem = mutableListOf<Quotes>()
            override fun onSuccess(data: List<Quotes>) {
                for (i in data) {
                    if (text == i.mQuotes) {
                        mlistQuotesOneItem.add(i)
                        mView?.switchActivityViewPlay(mlistQuotesOneItem)
                        mView?.loadingApiSuccess()
                        return
                    }
                }
            }

            override fun onError(exception: Exception?) {
                mView?.loadingApiSuccess()
            }
        })
    }
}
