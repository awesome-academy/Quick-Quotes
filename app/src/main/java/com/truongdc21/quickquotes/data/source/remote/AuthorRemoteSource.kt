package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.data.source.AuthorDataSource

class AuthorRemoteSource : AuthorDataSource.Remote {

    override fun getListAuthor(listener: OnRemoteResultListener<MutableList<String>>) {
        ApiConstance.parseJsontoAuthor(listener)
    }

    companion object {
        private var instance : AuthorRemoteSource? = null

        fun getInstance() = synchronized(this){
            instance ?: AuthorRemoteSource().also { instance = it }
        }
    }
}
