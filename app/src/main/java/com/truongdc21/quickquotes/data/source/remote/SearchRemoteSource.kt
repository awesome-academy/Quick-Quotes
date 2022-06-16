package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.api.ApiGetURL
import com.truongdc21.quickquotes.api.paserJsonAPIToList
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.SearchDataSource

class SearchRemoteSource: SearchDataSource.Remote {

    override fun getListQuotes(listener: OnRemoteResultListener<List<Quotes>>) {
        val uri = ApiGetURL.getURLAPI(ApiConstance.URL_QUOTES)
        paserJsonAPIToList.paserJsonAPIToListQuotes(uri , listener)
    }

    override fun getListAuthor(listener: OnRemoteResultListener<List<String>>) {
        val uri = ApiGetURL.getURLAPI(ApiConstance.URL_AUTHOR)
        paserJsonAPIToList.paserJsonAPIToListAuthor(uri,listener)
    }

    override fun getListTag(listener: OnRemoteResultListener<List<String>>) {
        val uri = ApiGetURL.getURLAPI(ApiConstance.URL_TAG)
        paserJsonAPIToList.paserJsonAPIToListTag(uri, listener)
    }

    companion object {
        private var instance: SearchRemoteSource? = null
        fun getInstance() = instance ?: SearchRemoteSource().also { instance = it }
    }
}
