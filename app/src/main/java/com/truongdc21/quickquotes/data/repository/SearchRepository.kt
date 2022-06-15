package com.truongdc21.quickquotes.data.repository

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.source.QuotesDataSource
import com.truongdc21.quickquotes.data.source.SearchDataSource
import com.truongdc21.quickquotes.data.source.TagDataSource
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

class SearchRepository(
    private val remote: SearchDataSource.Remote,
    private val local: SearchDataSource.Local
    ) : SearchDataSource.Local , SearchDataSource.Remote{

    override fun insertSearch(search: Search, listener: OnLocalResultListener<Unit>) {
        local.insertSearch(search , listener)
    }

    override fun updateSearch(search: Search, id: Int, listener: OnLocalResultListener<Unit>) {
        local.updateSearch(search, id , listener)
    }

    override fun deleteSearch(id: Int, listener: OnLocalResultListener<Unit>) {
        local.deleteSearch(id, listener)
    }

    override fun readSearch(listener: OnLocalResultListener<List<Search>>) {
        local.readSearch(listener)
    }

    override fun getListQuotes(listener: OnRemoteResultListener<List<Quotes>>) {
        remote.getListQuotes(listener)
    }

    override fun getListAuthor(listener: OnRemoteResultListener<List<String>>) {
        remote.getListAuthor(listener)
    }

    override fun getListTag(listener: OnRemoteResultListener<List<String>>) {
        remote.getListTag(listener)
    }

    companion object {
        private var instance: SearchRepository? = null

        fun getInstace(
            remote: SearchDataSource.Remote,
            local: SearchDataSource.Local
        ) = instance ?: SearchRepository(remote, local).also { instance = it }
    }
}
