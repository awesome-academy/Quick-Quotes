package com.truongdc21.quickquotes.data.source.remote

import java.lang.Exception

interface OnRemoteResultListener<T> {
    fun onSuccess(data: T)
    fun onError(exception: Exception?)
}
