package com.truongdc21.quickquotes.data.source

import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

interface TagDataSource{

    interface Local {
        fun insertTag (tag: String , listener: OnLocalResultListener<Unit>)
        fun updateTag (tag: String , id : Int , listener: OnLocalResultListener<Unit>)
        fun deleteTag (id : Int , listener: OnLocalResultListener<Unit>)
        fun readTag (listener: OnLocalResultListener<List<Tag>>)
    }

    interface Remote {
        fun getApiTagList (listener : OnRemoteResultListener<List<String>>)
    }
}
