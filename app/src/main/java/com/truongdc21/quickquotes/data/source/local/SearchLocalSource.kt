package com.truongdc21.quickquotes.data.source.local

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.source.SearchDataSource
import com.truongdc21.quickquotes.database.quotes.QuotesDaoDb
import com.truongdc21.quickquotes.database.search.SearchDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchLocalSource (private val searchDaoDb: SearchDao): SearchDataSource.Local {

    override fun insertSearch(search: Search, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                searchDaoDb.insertSearch(search)
                listener.onSuccess(Unit)
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun updateSearch(search: Search, id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                searchDaoDb.updateSearch(search, id)
                listener.onSuccess(Unit)
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun deleteSearch(id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                searchDaoDb.deleteSearch(id)
                listener.onSuccess(Unit)
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun readSearch(listener: OnLocalResultListener<List<Search>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listener.onSuccess(searchDaoDb.readSearch())
            }catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    companion object {
        private var instance: SearchLocalSource? = null
        fun getInstance(searchDaoDb: SearchDao) =
            instance ?: SearchLocalSource(searchDaoDb).also { instance = it }
    }
}
