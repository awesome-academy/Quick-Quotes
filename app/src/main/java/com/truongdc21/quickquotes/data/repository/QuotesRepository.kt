package com.truongdc21.quickquotes.data.repository

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.QuotesDataSource
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

class QuotesRepository (
    private val local : QuotesDataSource.Local,
    private val remote : QuotesDataSource.Remote
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

    companion object {
        private var instance: QuotesRepository? = null

        fun getInstance (local: QuotesDataSource.Local, remote: QuotesDataSource.Remote){
            synchronized(this){
                instance ?: QuotesRepository(local , remote).also { instance = it }
            }
        }
    }

}
