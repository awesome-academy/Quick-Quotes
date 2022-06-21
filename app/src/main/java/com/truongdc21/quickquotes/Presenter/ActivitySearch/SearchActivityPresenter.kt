package com.truongdc21.quickquotes.presenter.activitySearch

import android.content.Context
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.repository.SearchRepository
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.utils.Constant
import java.lang.Exception

class SearchActivityPresenter(
    private val mContext : Context,
    private val mRepoSearch : SearchRepository
    ): SearchActivityContract.Presenter{

    private var mView: SearchActivityContract.View? = null
    private var mlistSearchQuotes = mutableListOf<Search>()
    private var mlistSearchAuthor = mutableListOf<Search>()
    private var mlistSearchTag = mutableListOf<Search>()

    override fun onStart() {
        getListSearchAPIQuotes()
    }

    override fun onStop() {
    }

    override fun setView(view: SearchActivityContract.View?) {
        this.mView  = view
    }

    override fun getListSearchAPIQuotes() {
        mView?.loadingPropressbar()
        if (mlistSearchQuotes.isNotEmpty()){
            mView?.loadingSuccsess()
            mView?.sendSearchQuotestoFragment(mlistSearchQuotes)
            return
        }
        mRepoSearch.getListQuotes(object : OnRemoteResultListener<List<Quotes>> {
            override fun onSuccess(data: List<Quotes>) {
                for (i in data){
                    mlistSearchQuotes.add(Search(0 , ConstanceDb.COLUMN_QUOTES , i.mQuotes , Constant.REMOTE))
                }
                mView?.sendSearchQuotestoFragment(mlistSearchQuotes)
                mView?.loadingSuccsess()
            }

            override fun onError(exception: Exception?) {
                mView?.loadingSuccsess()
            }
        })
    }


    override fun getListSearchAPIAuthor() {
        mView?.loadingPropressbar()
        if (mlistSearchAuthor.isNotEmpty()){
            mView?.loadingSuccsess()
            mView?.sendSearchAuthorFragment(mlistSearchAuthor)
            return
        }
        mRepoSearch.getListAuthor(object : OnRemoteResultListener<List<String>> {
            override fun onSuccess(data: List<String>) {
                for (author in data){
                    mlistSearchAuthor.add(Search(0, ConstanceDb.COLUMN_AUTHOR, author, Constant.REMOTE))
                }
                mView?.sendSearchAuthorFragment(mlistSearchAuthor)
                mView?.loadingSuccsess()
            }
            override fun onError(exception: Exception?) {
                mView?.loadingSuccsess()
            }
        })
    }

    override fun getListSearchAPITag() {
        mView?.loadingPropressbar()
        if (mlistSearchTag.isNotEmpty()){
            mView?.loadingSuccsess()
            mView?.sendSearchTagFragment(mlistSearchTag)
            return
        }
        mRepoSearch.getListTag(object : OnRemoteResultListener<List<String>> {
            override fun onSuccess(data: List<String>) {
                for (tag in data){
                    mlistSearchTag.add(Search(0, ConstanceDb.COLUMN_TAG, tag, Constant.REMOTE))
                }
                mView?.loadingSuccsess()
                mView?.sendSearchTagFragment(mlistSearchTag)
            }
            override fun onError(exception: Exception?) {
            }
        })
    }

    override fun setPositionItemQuotes(position: Int) {
        when(position){
            Constant.TAB_QUOTES -> getListSearchAPIQuotes()

            Constant.TAB_AUTHOR -> getListSearchAPIAuthor()

            Constant.TAB_TAG -> getListSearchAPITag()
        }
    }
}
