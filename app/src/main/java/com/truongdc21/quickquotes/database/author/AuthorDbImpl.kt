package com.truongdc21.quickquotes.database.author

import android.content.ContentValues
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.database.MyDatabaseHelper
import com.truongdc21.quickquotes.data.model.Author

class AuthorDbImpl(database: MyDatabaseHelper): AuthorDaoDb {

    val dbRead = database.readableDatabase
    val dbWrite = database.writableDatabase

    override suspend fun insertAuthor(author: String) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_AUTHOR, author)
        }
        dbWrite.insert(ConstanceDb.TABLE_NAME_AUTHOR, null, cv)

    }

    override suspend fun updateAuthor(author: String, id: Int) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_AUTHOR, author)
        }
        dbWrite.update(
            ConstanceDb.TABLE_NAME_AUTHOR,
            cv,
            "${ConstanceDb.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
    }

    override suspend fun deleteAuthor(id: Int) {
        dbWrite.delete(
            ConstanceDb.TABLE_NAME_AUTHOR,
            "${ConstanceDb.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
    }

    override suspend fun readAuthor(): List<Author> {
        val mListAuthor = mutableListOf<Author>()
        val query = "SELECT * FROM " + ConstanceDb.TABLE_NAME_AUTHOR
        val cursor = dbRead.rawQuery(query, null)
        cursor?.let {
            if (it.count != 0) {
                while (it.moveToNext()) {
                    val author = Author(it.getInt(0), it.getString(1))
                    mListAuthor.add(author)
                }
            }
        }
        return mListAuthor.reversed()
    }

    companion object {
        private var instance : AuthorDbImpl? = null
        fun getInstance(database: MyDatabaseHelper) = synchronized(this){
            instance?: AuthorDbImpl(database).also { instance = it }
        }
    }
}
