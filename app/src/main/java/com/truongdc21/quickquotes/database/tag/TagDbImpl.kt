package com.truongdc21.quickquotes.database.tag

import android.content.ContentValues
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.database.MyDatabaseHelper
import com.truongdc21.quickquotes.data.model.Tag

class TagDbImpl(database: MyDatabaseHelper) : TagDaoDb {
    private val dbWrite = database.writableDatabase
    private val dbRead = database.readableDatabase

    override suspend fun insertTag(tag: String) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_TAG, tag)
        }
        dbWrite.insert(ConstanceDb.TABLE_NAME_TAG, null, cv)
    }

    override suspend fun updateTag(tag: String, id: Int) {
        val cv = ContentValues().apply {
            put(ConstanceDb.COLUMN_TAG, tag)
        }
        dbWrite.update(ConstanceDb.TABLE_NAME_TAG, cv, "id=?", arrayOf(id.toString()))
    }

    override suspend fun deleteTag(id: Int) {
        dbWrite.delete(ConstanceDb.TABLE_NAME_TAG, "id =?", arrayOf(id.toString()))
        dbWrite.close()
    }

    override suspend fun readTag(): List<Tag> {
        val mListTag = mutableListOf<Tag>()
        val query = "SELECT * FROM " + ConstanceDb.TABLE_NAME_TAG
        val cursor = dbRead.rawQuery(query, null)
        cursor?.let {
            if (it.count != 0) {
                while (it.moveToNext()) {
                    val tag = Tag(it.getString(0), it.getString(1))
                    mListTag.add(tag)
                }
            }
        }
        return mListTag
    }

    companion object {
        private var instance : TagDbImpl? = null
        fun getInstance(database: MyDatabaseHelper) = synchronized(this){
            instance?: TagDbImpl(database).also { instance = it }
        }
    }
}
