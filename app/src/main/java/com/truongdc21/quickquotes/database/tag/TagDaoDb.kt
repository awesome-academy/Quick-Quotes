package com.truongdc21.quickquotes.database.tag

import com.truongdc21.quickquotes.data.model.Tag

interface TagDaoDb {

    suspend fun insertTag (tag: String)

    suspend fun updateTag (tag: String , id: Int)

    suspend fun deleteTag (id: Int)

    suspend fun readTag (): List<Tag>
}
