package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.api.ApiGetURL
import com.truongdc21.quickquotes.api.HttpConnectAPI
import com.truongdc21.quickquotes.api.paserJsonAPIToList
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.QuotesDataSource

class QuotesRemoteSource : QuotesDataSource.Remote{

    override fun getApiQuotesList(listener: OnRemoteResultListener<List<Quotes>>) {
        val apiRandom = ApiGetURL.getURLRandomQuotes(ApiConstance.URL_QUOTES_RANDOM)
        paserJsonAPIToList.paserJsonAPIToListQuotes(apiRandom , listener)
    }

    companion object {
        private var instance: QuotesRemoteSource? = null
        fun getInstance() = instance ?: QuotesRemoteSource().also { instance = it }
    }
}
