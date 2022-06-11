package com.truongdc21.quickquotes.database.quotes

import android.content.ContentValues
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.database.MyDatabaseHelper
import com.truongdc21.quickquotes.data.model.Quotes

class QuotesDbImpl(database: MyDatabaseHelper) : QuotesDaoDb {
    val dbRead = database.readableDatabase
    val dbWrite = database.writableDatabase

    override suspend fun insertQuotes(quotes: Quotes) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_QUOTES, quotes.mQuotes)
            put(ConstanceDb.COLUMN_AUTHOR, quotes.Author)
            put(ConstanceDb.COLUMN_TAG, quotes.Tag)
        }
        dbWrite.insert(ConstanceDb.TABLE_NAME_QUOTES, null, cv)
    }

    override suspend fun updateQuotes(quotes: Quotes, id: Int) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_QUOTES, quotes.mQuotes)
            put(ConstanceDb.COLUMN_AUTHOR, quotes.Author)
            put(ConstanceDb.COLUMN_TAG, quotes.Tag)
        }
        dbWrite.update(
            ConstanceDb.TABLE_NAME_QUOTES,
            cv,
            "${ConstanceDb.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
    }

    override suspend fun deleteQuotes(id: Int) {
        dbWrite.delete(
            ConstanceDb.TABLE_NAME_QUOTES,
            "${ConstanceDb.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
        dbWrite.close()
    }

    override suspend fun readQuotes(): List<Quotes> {
        val mListQuotes = mutableListOf<Quotes>()
        val query = "SELECT * FROM " + ConstanceDb.TABLE_NAME_QUOTES
        val cursor = dbRead.rawQuery(query, null)
        cursor?.let {
            if (it.count != 0) {
                while (it.moveToNext()) {
                    val quotes =
                        Quotes(it.getInt(0), it.getString(1), it.getString(2), it.getString(3))
                    mListQuotes.add(quotes)
                }
            }
        }
        return mListQuotes
    }

    companion object {
        private var instance : QuotesDbImpl? = null
        fun getInstance(database: MyDatabaseHelper) = synchronized(this){
            instance?: QuotesDbImpl(database).also { instance = it }
        }
    }
}
