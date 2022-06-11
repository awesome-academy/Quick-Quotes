package com.truongdc21.quickquotes.api

import android.net.Uri
import android.util.Log
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import kotlin.math.log

object ApiConstance {
    const val URL_QUOTES = "https://goquotes-api.herokuapp.com/api/v1/all/quotes"
    const val URL_AUTHOR = "https://goquotes-api.herokuapp.com/api/v1/all/authors"
    const val URL_TAG = "https://goquotes-api.herokuapp.com/api/v1/all/tags"
    const val URL_QUOTES_RANDOM = "https://goquotes-api.herokuapp.com/api/v1/random?"

    const val QUOTES_TEXT = "text"
    const val QUOTES_AUTHOR = "author"
    const val QUOTES_TAG = "tag"

    const val AUTHOR_NAME = "name"

    const val TAG_NAME = "name"

    const val NAME_OBJECT_JSON_ARRAY_QUOTES = "quotes"
    const val NAME_OBJECT_JSON_ARRAY_AUTHOR = "authors"
    const val NAME_OBJECT_JSON_ARRAY_TAG = "tags"

    const val QUERY_COUNT_RANDOM_QUOTES = "count"
    const val QUERY_KEY_RANDOM_QUOTES = "81"

    const val METHOD_GET = "GET"
    const val TIME_OUT_READ = 15000

    private fun getURLformAPI(URL: String ): Uri{
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .build()
        return builtURI
    }

    private fun getURLformAPIQuotesRandom(URL: String ): Uri{
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .appendQueryParameter(QUERY_COUNT_RANDOM_QUOTES , QUERY_KEY_RANDOM_QUOTES)
            .build()
        return builtURI
    }

    private fun connectAPI(
        URI : Uri,
        NAME_OBJECT_JSON_ARRAY : String) : JSONArray{
        val requestURL = URL(URI.toString())
        val connection = requestURL.openConnection() as HttpURLConnection
        connection.run {
            requestMethod = METHOD_GET
            readTimeout = TIME_OUT_READ
            connectTimeout = TIME_OUT_READ
            connect()
        }
        val data =  connection.inputStream.bufferedReader().readText()
        val json = JSONObject(data)
        return json.optJSONArray(NAME_OBJECT_JSON_ARRAY)
    }

    fun parseJsontoQuotes(listener: OnRemoteResultListener<List<Quotes>>) {
        CoroutineScope(Job() + Dispatchers.IO).launch{
            try {
                val uri = getURLformAPIQuotesRandom(URL_QUOTES_RANDOM)
                val jsonArrayQuotes = connectAPI(uri, NAME_OBJECT_JSON_ARRAY_QUOTES)
                withContext(Dispatchers.Default){
                    val listImage = APiImage.getIMGfromMockapi(APiImage.URL_IMAGE)
                    val listQuotes = mutableListOf<Quotes>()
                    for (i in 0 until jsonArrayQuotes.length()){
                        val jsonQuotes = jsonArrayQuotes.optJSONObject(i)
                        val srtQuotes = jsonQuotes.optString(QUOTES_TEXT)
                        val srtAuthor = jsonQuotes.optString(QUOTES_AUTHOR)
                        val srtTag = jsonQuotes.optString(QUOTES_TAG)
                        val random1 = (0..listImage.size-1).shuffled().last()
                        val quotes = Quotes( 0,srtQuotes, srtAuthor, srtTag, listImage[random1])
                        listQuotes.add(quotes)
                    }
                    withContext(Dispatchers.Main){ listener.onSuccess(listQuotes) }
                }
            }catch (e : Exception){
                withContext(Dispatchers.Main){listener.onError(e)}
            }
        }
    }

    fun parseJsontoAuthor(listener: OnRemoteResultListener<MutableList<String>>) {
        try {
            val jsonArrayQuotes = connectAPI( getURLformAPI(URL_AUTHOR) , NAME_OBJECT_JSON_ARRAY_AUTHOR)
            val listAuthor = mutableListOf<String>()
            for (i in 0 until jsonArrayQuotes.length()){
                val jsonQuotes = jsonArrayQuotes.optJSONObject(i)
                val srtAuthor = jsonQuotes.optString(AUTHOR_NAME)
                listAuthor.add(srtAuthor)
            }
            listener.onSuccess(listAuthor)
        }catch (e: Exception) {
            listener.onError(e)
        }
    }

    fun parseJsontoTag(listener: OnRemoteResultListener<List<String>>) =
        CoroutineScope(Dispatchers.IO).launch {
        try {
            val jsonArrayQuotes = connectAPI( getURLformAPI(URL_TAG) ,NAME_OBJECT_JSON_ARRAY_TAG)
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
