package com.truongdc21.quickquotes.data.source.local

import com.truongdc21.quickquotes.database.author.AuthorDaoDb
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.source.AuthorDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorLocalSource(private val authorDaodb : AuthorDaoDb ): AuthorDataSource.Local {
    override fun insertAuthor(author: String, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authorDaodb.insertAuthor(author)
                listener.onSuccess(Unit)
            }
            catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun updateAuthor(author: String, id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authorDaodb.updateAuthor(author , id)
                listener.onSuccess(Unit)
            }
            catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun deleteAuthor(id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                authorDaodb.deleteAuthor(id)
                listener.onSuccess(Unit)
            }
            catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    override fun readAuthor(listener: OnLocalResultListener<MutableList<Author>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listener.onSuccess(authorDaodb.readAuthor())
            }
            catch (e : Exception){
                listener.onError(e)
            }
        }
    }

    companion object {
        private var instance: AuthorLocalSource? = null

        fun getInstance(authorDaodb: AuthorDaoDb) = synchronized(this) {
            instance ?: AuthorLocalSource(authorDaodb).also { instance = it }
        }
    }
}
