package com.truongdc21.quickquotes.presenter.MainActivity

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.utils.Constant
import java.lang.Exception

class MainActivityPresenter(
    private val mRepoQuotes : QuotesRepository
    ):MainActivityContract.Persenter{

    private var mView : MainActivityContract.View? = null

    override fun onStart() {
        getApiList()
    }

    override fun onStop() {
    }

    override fun setView(view: MainActivityContract.View?) {
        mView = view
    }

    override fun getApiList() {
        mRepoQuotes.getApiQuotesWithTag(
            Constant.KEY_MORNING,
            object : OnRemoteResultListener<List<Quotes>>{

            override fun onSuccess(data: List<Quotes>) {
                val random = (0..data.size-1).shuffled().last()
                mView?.showQuotesNotification(data[random])
            }
            override fun onError(exception: Exception?) {}
        })
    }
}
