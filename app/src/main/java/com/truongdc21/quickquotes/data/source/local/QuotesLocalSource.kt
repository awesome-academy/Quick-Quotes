package com.truongdc21.quickquotes.data.source.local


import com.truongdc21.quickquotes.database.quotes.QuotesDaoDb
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.QuotesDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesLocalSource(private val quotesDaoDb: QuotesDaoDb): QuotesDataSource.Local{
    override fun insertQuotes(quotes: Quotes, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                quotesDaoDb.insertQuotes(quotes)
                listener.onSuccess(Unit)
            }
            catch (e : Exception){ listener.onError(e) }
        }
    }

    override fun updateQuotes(quotes: Quotes, id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                quotesDaoDb.updateQuotes(quotes , id)
                listener.onSuccess(Unit)
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun deleteQuotes(id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                quotesDaoDb.deleteQuotes(id)
                listener.onSuccess(Unit)
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun readQuotes(listener: OnLocalResultListener<List<Quotes>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listener.onSuccess(quotesDaoDb.readQuotes())
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    companion object {
        private var instance: QuotesLocalSource? = null
        fun getInstance(quotesDaoDb: QuotesDaoDb) =
            instance ?: QuotesLocalSource(quotesDaoDb).also { instance = it }
    }
}
