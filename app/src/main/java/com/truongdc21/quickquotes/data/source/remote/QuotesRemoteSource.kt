package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.api.ApiGetURL
import com.truongdc21.quickquotes.api.paserJsonAPIToList
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.QuotesDataSource

class QuotesRemoteSource : QuotesDataSource.Remote{

    override fun getApiQuotesList(listener: OnRemoteResultListener<List<Quotes>>) {
        val apiRandom = ApiGetURL.getURLRandomQuotes(ApiConstance.URL_QUOTES_RANDOM)
        paserJsonAPIToList.paserJsonAPIToListQuotes(apiRandom , listener)
    }

    override fun getApiQuotesWithAuthor(key : String, listener: OnRemoteResultListener<List<Quotes>>) {
        val apiRandom = ApiGetURL.getURLAuthor(ApiConstance.URL_AUTHOR_TAG , key )
        paserJsonAPIToList.paserJsonAPIToListQuotes(apiRandom , listener)
    }

    override fun getApiQuotesWithTag(key: String, listener: OnRemoteResultListener<List<Quotes>>) {
        val apiRandom = ApiGetURL.getURLTag(ApiConstance.URL_AUTHOR_TAG , key )
        paserJsonAPIToList.paserJsonAPIToListQuotes(apiRandom , listener)
    }

    companion object {
        private var instance: QuotesRemoteSource? = null
        fun getInstance() = instance ?: QuotesRemoteSource().also { instance = it }
    }
}
