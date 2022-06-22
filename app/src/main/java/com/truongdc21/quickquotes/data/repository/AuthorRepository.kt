package com.truongdc21.quickquotes.data.repository

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.source.AuthorDataSource
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

class AuthorRepository(
    private val remote : AuthorDataSource.Remote,
    private val local : AuthorDataSource.Local
 ) : AuthorDataSource.Local , AuthorDataSource.Remote{

    override fun insertAuthor(author: String, listener: OnLocalResultListener<Unit>) {
        local.insertAuthor(author , listener)
    }

    override fun updateAuthor(author: String, id: Int, listener: OnLocalResultListener<Unit>) {
        local.updateAuthor(author , id , listener)
    }

    override fun deleteAuthor(id: Int, listener: OnLocalResultListener<Unit>) {
        local.deleteAuthor(id , listener)
    }

    override fun readAuthor(listener: OnLocalResultListener<List<Author>>) {
        local.readAuthor(listener)
    }

    override fun getListAuthor(listener: OnRemoteResultListener<List<String>>) {
        remote.getListAuthor(listener)
    }

    companion object {
        private var instance: AuthorRepository? = null
        fun getInstace(
            remote: AuthorDataSource.Remote,
            local: AuthorDataSource.Local) = AuthorRepository.instance
            ?: AuthorRepository(remote, local).also { AuthorRepository.instance = it }
    }
}
