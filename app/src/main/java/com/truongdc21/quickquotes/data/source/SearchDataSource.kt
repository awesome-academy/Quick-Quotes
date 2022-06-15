package com.truongdc21.quickquotes.data.source

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

interface SearchDataSource {

    interface Local {
        fun insertSearch (search: Search, listener: OnLocalResultListener<Unit>)
        fun updateSearch (search: Search, id : Int , listener: OnLocalResultListener<Unit>)
        fun deleteSearch (id : Int , listener: OnLocalResultListener<Unit>)
        fun readSearch (listener: OnLocalResultListener<List<Search>>)
    }

    interface Remote {
        fun getListQuotes (listener : OnRemoteResultListener<List<Quotes>>)
        fun getListAuthor (listener : OnRemoteResultListener<List<String>>)
        fun getListTag (listener : OnRemoteResultListener<List<String>>)
    }
}