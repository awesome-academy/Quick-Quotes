package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.api.ApiGetURL
import com.truongdc21.quickquotes.api.paserJsonAPIToList
import com.truongdc21.quickquotes.data.source.AuthorDataSource

class AuthorRemoteSource : AuthorDataSource.Remote {

    override fun getListAuthor(listener: OnRemoteResultListener<List<String>>) {
        val uri = ApiGetURL.getURLAPI(ApiConstance.URL_AUTHOR)
        paserJsonAPIToList.paserJsonAPIToListAuthor(uri,listener)
    }

    companion object {
        private var instance : AuthorRemoteSource? = null

        fun getInstance() = synchronized(this){
            instance ?: AuthorRemoteSource().also { instance = it }
        }
    }
}
