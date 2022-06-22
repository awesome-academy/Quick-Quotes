package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.api.ApiGetURL
import com.truongdc21.quickquotes.api.paserJsonAPIToList
import com.truongdc21.quickquotes.data.source.TagDataSource

class TagRemoteSource : TagDataSource.Remote {

    override fun getApiTagList(listener: OnRemoteResultListener<List<String>>) {
        val uri = ApiGetURL.getURLAPI(ApiConstance.URL_TAG)
        paserJsonAPIToList.paserJsonAPIToListTag(uri, listener)
    }

    companion object {
        private var instance: TagRemoteSource? = null

        fun getInstance() = synchronized(this){
            instance ?: TagRemoteSource().also { instance = it }
        }
    }
}
