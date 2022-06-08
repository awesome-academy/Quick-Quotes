package com.truongdc21.quickquotes.data.source.local

import java.lang.Exception

interface OnLocalResultListener<T> {
    fun onSuccess(data: T)
    fun onError(exception: Exception?)
}
