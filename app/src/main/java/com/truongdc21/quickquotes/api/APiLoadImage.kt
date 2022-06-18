package com.truongdc21.quickquotes.api

import android.net.Uri
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

object APiLoadImage {

    const val URL_IMAGE = "https://62a4325a47e6e400638de307.mockapi.io/imagefromFireBase"

    fun getIMGfromMockapi(URL: String ) : List<String> {
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .build()
        val requestURL = URL(builtURI.toString())
        val connection = requestURL.openConnection() as HttpURLConnection
        connection.run {
            requestMethod = ApiConstance.METHOD_GET
            readTimeout = ApiConstance.TIME_OUT_READ
            connectTimeout = ApiConstance.TIME_OUT_READ
            connect()
        }
        val data =  connection.inputStream.bufferedReader().readText()
        val jsonArray = JSONArray(data)
        val listURl = mutableListOf<String>()
        for (i in 0 until jsonArray.length()){
            val jsonQuotes = jsonArray.optJSONObject(i)
            val srtURl = jsonQuotes.optString("url")
            listURl.add(srtURl)
        }
        return listURl
    }
}
