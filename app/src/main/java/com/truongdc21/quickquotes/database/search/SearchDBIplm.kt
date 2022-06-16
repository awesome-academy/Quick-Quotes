package com.truongdc21.quickquotes.database.search

import android.content.ContentValues
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.database.MyDatabaseHelper

class SearchDBIplm(database: MyDatabaseHelper): SearchDao {

    val dbRead = database.readableDatabase
    val dbWrite = database.writableDatabase

    override suspend fun insertSearch(search: Search) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_KEY, search.key)
            put(ConstanceDb.COLUMN_TEXT, search.text)
            put(ConstanceDb.COLUMN_TYPE, search.type)
        }
        dbWrite.insert(ConstanceDb.TABLE_NAME_SEARCH, null, cv)
    }

    override suspend fun updateSearch(search: Search, id: Int) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_KEY, search.key)
            put(ConstanceDb.COLUMN_TEXT, search.text)
            put(ConstanceDb.COLUMN_TYPE, search.type)
        }
        dbWrite.update(
            ConstanceDb.TABLE_NAME_SEARCH,
            cv,
            "${ConstanceDb.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
    }

    override suspend fun deleteSearch(id: Int) {
        dbWrite.delete(
            ConstanceDb.TABLE_NAME_SEARCH,
            "${ConstanceDb.COLUMN_ID}=?",
            arrayOf(id.toString())
        )
    }

    override suspend fun readSearch(): List<Search> {
        val mListSearch = mutableListOf<Search>()
        val query = "SELECT * FROM " + ConstanceDb.TABLE_NAME_SEARCH
        val cursor = dbRead.rawQuery(query, null)
        cursor?.let {
            if (it.count != 0) {
                while (it.moveToNext()) {
                    val search =
                        Search(it.getInt(0), it.getString(1), it.getString(2) ,it.getString(3))
                    mListSearch.add(search)
                }
            }
        }
        return mListSearch
    }
    companion object {
        private var instance : SearchDBIplm? = null
        fun getInstance(database: MyDatabaseHelper) = synchronized(this){
            instance?: SearchDBIplm(database).also { instance = it }
        }
    }
}
