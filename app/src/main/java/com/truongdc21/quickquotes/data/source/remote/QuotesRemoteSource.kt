package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.QuotesDataSource

class QuotesRemoteSource : QuotesDataSource.Remote{

    override fun getApiQuotesList(listener: OnRemoteResultListener<List<Quotes>>) {
        ApiConstance.parseJsontoQuotes(listener)
    }

    companion object {
        private var instance : QuotesRemoteSource? = null
        fun getInstance() = synchronized(this){
            instance ?: QuotesRemoteSource().also { instance = it }
        }
    }
}
