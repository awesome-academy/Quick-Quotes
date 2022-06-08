package com.truongdc21.quickquotes.api

import android.net.Uri
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object ApiConstance {

    const val URL_QUOTES = "https://goquotes-api.herokuapp.com/api/v1/all/quotes"
    const val URL_AUTHOR = "https://goquotes-api.herokuapp.com/api/v1/all/authors"
    const val URL_TAG = "https://goquotes-api.herokuapp.com/api/v1/all/tags"

    const val QUOTES_TEXT = "text"
    const val QUOTES_AUTHOR = "author"
    const val QUOTES_TAG = "tag"

    const val AUTHOR_NAME = "name"

    const val TAG_NAME = "name"

    const val NAME_OBJECT_JSON_ARRAY_QUOTES = "quotes"
    const val NAME_OBJECT_JSON_ARRAY_AUTHOR = "authors"
    const val NAME_OBJECT_JSON_ARRAY_TAG = "tags"

    const val METHOD_GET = "GET"
    const val TIME_OUT_READ = 15000

    fun connectAPI (
        URL : String ,
        NAME_OBJECT_JSON_ARRAY : String) = CoroutineScope(Dispatchers.IO).async{
        val builtURI: Uri = Uri.parse(URL).buildUpon().build()
        val requestURL = URL(builtURI.toString())
        val connection = requestURL.openConnection() as HttpURLConnection
        connection.run {
            requestMethod = METHOD_GET
            readTimeout = TIME_OUT_READ
            connectTimeout = TIME_OUT_READ
            connect()
        }
        val data =  connection.inputStream.bufferedReader().readText()
        val json = JSONObject(data)
        return@async json.optJSONArray(NAME_OBJECT_JSON_ARRAY)
    }

    fun parseJsontoQuotes (listener: OnRemoteResultListener<List<Quotes>>) = CoroutineScope(Dispatchers.IO).launch{
        try {
            val jsonArrayQuotes = connectAPI(URL_QUOTES , NAME_OBJECT_JSON_ARRAY_QUOTES).await()
            withContext(Dispatchers.Default){
                val listQuotes = mutableListOf<Quotes>()
                for (i in 0 until jsonArrayQuotes.length()){
                    val jsonQuotes = jsonArrayQuotes.optJSONObject(i)
                    val srtQuotes = jsonQuotes.optString(QUOTES_TEXT)
                    val srtAuthor = jsonQuotes.optString(QUOTES_AUTHOR)
                    val srtTag = jsonQuotes.optString(QUOTES_TAG)
                    val quotes = Quotes( 0,srtQuotes, srtAuthor, srtTag)
                    listQuotes.add(quotes)
                }
                listener.onSuccess(listQuotes)
            }
        }catch (e : Exception){
            listener.onError(e)
        }
    }

    fun parseJsontoAuthor (listener: OnRemoteResultListener<MutableList<String>>) = CoroutineScope(Dispatchers.IO).launch{
        try {
            val jsonArrayQuotes = connectAPI( URL_AUTHOR , NAME_OBJECT_JSON_ARRAY_AUTHOR).await()
            withContext(Dispatchers.Default){
                val listAuthor = mutableListOf<String>()
                for (i in 0 until jsonArrayQuotes.length()){
                    val jsonQuotes = jsonArrayQuotes.optJSONObject(i)
                    val srtAuthor = jsonQuotes.optString(AUTHOR_NAME)
                    listAuthor.add(srtAuthor)
                }
                listener.onSuccess(listAuthor)
            }
        }catch (e: Exception) {
            listener.onError(e)
        }
    }

    fun parseJsontoTag (listener: OnRemoteResultListener<List<String>>) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val jsonArrayQuotes = connectAPI( URL_TAG,NAME_OBJECT_JSON_ARRAY_TAG).await()
            withContext(Dispatchers.Default) {
                val listTag = mutableListOf<String>()
                for (i in 0 until jsonArrayQuotes.length()){
                    val jsonQuotes = jsonArrayQuotes.optJSONObject(i)
                    val srtTag = jsonQuotes.optString(TAG_NAME)
                    listTag.add(srtTag)
                }
                listener.onSuccess(listTag)
            }
        }catch (e: Exception){
            listener.onError(e)
        }
    }
}
