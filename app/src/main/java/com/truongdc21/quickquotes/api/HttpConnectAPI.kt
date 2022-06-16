package com.truongdc21.quickquotes.api

import android.net.Uri
import java.net.HttpURLConnection
import java.net.URL

object HttpConnectAPI {

    const val METHOD_GET = "GET"
    const val TIME_OUT_READ = 15000

    fun connectHttpAPI(uri: Uri): HttpURLConnection {
        val requestURL = URL(uri.toString())
        val connection = requestURL.openConnection() as HttpURLConnection
        connection.run {
            requestMethod = METHOD_GET
            readTimeout = TIME_OUT_READ
            connectTimeout = TIME_OUT_READ
            connect()
        }
        return connection
    }
}
