package com.truongdc21.quickquotes.database.author

import com.truongdc21.quickquotes.data.model.Author

interface AuthorDaoDb {

    suspend fun insertAuthor(author: String)

    suspend fun updateAuthor(author: String, id : Int)

    suspend fun deleteAuthor(id : Int)

    suspend fun readAuthor(): List<Author>
}
