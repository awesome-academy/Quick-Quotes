package com.truongdc21.quickquotes.data.repository

import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.source.TagDataSource
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

class TagRepository (
    private val local : TagDataSource.Local,
    private val remote : TagDataSource.Remote
    ): TagDataSource.Local , TagDataSource.Remote{

    override fun insertTag(tag: String, listener: OnLocalResultListener<Unit>) {
        local.insertTag(tag, listener)
    }

    override fun updateTag(tag: String, id: Int, listener: OnLocalResultListener<Unit>) {
        local.updateTag(tag , id,  listener)
    }

    override fun deleteTag(id: Int, listener: OnLocalResultListener<Unit>) {
        local.deleteTag(id ,listener)
    }

    override fun readTag(listener: OnLocalResultListener<List<Tag>>) {
        local.readTag(listener)
    }

    override fun getApiTagList(listener: OnRemoteResultListener<List<String>>) {
        remote.getApiTagList(listener)
    }

    companion object {
        private var instance: TagRepository? = null

        fun getInstance(local: TagDataSource.Local, remote: TagDataSource.Remote){
            synchronized(this){
                instance ?: TagRepository(local , remote).also { instance = it }
            }
        }
    }
}
