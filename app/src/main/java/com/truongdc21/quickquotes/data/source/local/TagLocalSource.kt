package com.truongdc21.quickquotes.data.source.local

import com.truongdc21.quickquotes.database.tag.TagDaoDb
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.source.TagDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagLocalSource(private val tagDaoDb: TagDaoDb): TagDataSource.Local {

    override fun insertTag(tag: String, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tagDaoDb.insertTag(tag)
                listener.onSuccess(Unit)
            }catch (e: Exception){
                listener.onError(e)
            }
        }
    }

    override fun updateTag(tag: String, id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tagDaoDb.updateTag(tag , id)
                listener.onSuccess(Unit)
            }catch (e: Exception){
                listener.onError(e)
            }
        }
    }

    override fun deleteTag(id: Int, listener: OnLocalResultListener<Unit>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                tagDaoDb.deleteTag(id)
                listener.onSuccess(Unit)
            }catch (e: Exception){
                listener.onError(e)
            }
        }
    }

    override fun readTag(listener: OnLocalResultListener<List<Tag>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                listener.onSuccess(tagDaoDb.readTag())
            }catch (e: Exception){
                listener.onError(e)
            }
        }
    }

    companion object {
        private var instance: TagLocalSource? = null
        fun getInstance (tagDaoDb: TagDaoDb) = synchronized(this) {
            instance ?: TagLocalSource(tagDaoDb).also { instance = it }
        }
    }
}
