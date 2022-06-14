package com.truongdc21.quickquotes.data.source

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

interface AuthorDataSource {

    interface Local {
        fun insertAuthor (author: String , listener: OnLocalResultListener<Unit>)
        fun updateAuthor (author: String , id : Int , listener: OnLocalResultListener<Unit>)
        fun deleteAuthor (id : Int , listener: OnLocalResultListener<Unit>)
        fun readAuthor (listener: OnLocalResultListener<MutableList<Author>>)
    }

    interface Remote {
        fun getListAuthor (listener : OnRemoteResultListener<MutableList<String>>)
    }
}
