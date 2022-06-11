package com.truongdc21.quickquotes.data.source

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

interface QuotesDataSource {

    interface Local {
        fun insertQuotes (quotes: Quotes , listener: OnLocalResultListener<Unit>)
        fun updateQuotes (quotes: Quotes , id : Int , listener: OnLocalResultListener<Unit>)
        fun deleteQuotes (id : Int , listener: OnLocalResultListener<Unit>)
        fun readQuotes (listener: OnLocalResultListener<List<Quotes>>)
    }

    interface Remote {
        fun getApiQuotesList (listener : OnRemoteResultListener<List<Quotes>>)
    }
}
