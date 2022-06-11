package com.truongdc21.quickquotes.data.source.remote

import com.truongdc21.quickquotes.api.ApiConstance
import com.truongdc21.quickquotes.data.source.TagDataSource

class TagRemoteSource : TagDataSource.Remote {

    override fun getApiTagList(listener: OnRemoteResultListener<List<String>>) {
        ApiConstance.parseJsontoTag(listener)
    }
}