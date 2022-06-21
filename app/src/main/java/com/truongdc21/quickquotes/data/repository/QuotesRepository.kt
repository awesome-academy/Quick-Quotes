package com.truongdc21.quickquotes.data.repository

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.QuotesDataSource
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

class QuotesRepository(
    private val remote : QuotesDataSource.Remote,
    private val local : QuotesDataSource.Local
    ): QuotesDataSource.Remote , QuotesDataSource.Local{

    override fun insertQuotes(quotes: Quotes, listener: OnLocalResultListener<Unit>) {
        local.insertQuotes(quotes, listener)
    }

    override fun updateQuotes(quotes: Quotes, id: Int, listener: OnLocalResultListener<Unit>) {
        local.updateQuotes(quotes , id , listener)
    }

    override fun deleteQuotes(id: Int, listener: OnLocalResultListener<Unit>) {
        local.deleteQuotes(id, listener)
    }

    override fun readQuotes(listener: OnLocalResultListener<List<Quotes>>) {
        local.readQuotes(listener)
    }

    override fun getApiQuotesList(listener: OnRemoteResultListener<List<Quotes>>) {
        remote.getApiQuotesList(listener)
    }

    override fun getApiQuotesWithAuthor(key : String, listener: OnRemoteResultListener<List<Quotes>>) {
        remote.getApiQuotesWithAuthor(key ,listener)
    }

    override fun getApiQuotesWithTag(key : String, listener: OnRemoteResultListener<List<Quotes>>) {
        remote.getApiQuotesWithTag(key ,listener)
    }

    companion object {
        private var instance: QuotesRepository? = null

        fun getInstace(
            remote: QuotesDataSource.Remote,
            local: QuotesDataSource.Local
        ) = instance ?: QuotesRepository(remote, local).also { instance = it }
    }
}
