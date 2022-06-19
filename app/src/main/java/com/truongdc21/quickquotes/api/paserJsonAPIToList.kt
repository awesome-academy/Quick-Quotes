package com.truongdc21.quickquotes.api

import android.net.Uri
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import kotlinx.coroutines.*
import org.json.JSONObject

object paserJsonAPIToList {

    const val NAME_OBJECT_JSON_ARRAY_QUOTES = "quotes"
    const val NAME_OBJECT_JSON_ARRAY_AUTHOR = "authors"
    const val NAME_OBJECT_JSON_ARRAY_TAG = "tags"

    fun paserJsonAPIToListQuotes (uri : Uri, listener: OnRemoteResultListener<List<Quotes>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val httpConnect = HttpConnectAPI.connectHttpAPI(uri)
                val data =  httpConnect.inputStream.bufferedReader().readText()
                val listImage = APiLoadImage.getIMGfromMockapi(APiLoadImage.URL_IMAGE)
                withContext(Dispatchers.Default){
                    val json = JSONObject(data)
                    val jsonArray = json.optJSONArray(NAME_OBJECT_JSON_ARRAY_QUOTES)
                    val listQuotes = mutableListOf<Quotes>()
                    for (i in 0 until jsonArray.length()){
                        val jsonQuotes = jsonArray.optJSONObject(i)
                        val srtQuotes = jsonQuotes.optString(ApiConstance.QUOTES_TEXT)
                        val srtAuthor = jsonQuotes.optString(ApiConstance.QUOTES_AUTHOR)
                        val srtTag = jsonQuotes.optString(ApiConstance.QUOTES_TAG)
                        val random1 = (0..listImage.size-1).shuffled().last()
                        val quotes = Quotes( 0,srtQuotes, srtAuthor, srtTag, listImage[random1])
                        listQuotes.add(quotes)
                    }
                    withContext(Dispatchers.Main){listener.onSuccess(listQuotes)}
                }
            }catch (e : Exception){
                listener.onError(e)
                this.cancel()
            }
        }
    }

    fun paserJsonAPIToListAuthor (uri: Uri, listener: OnRemoteResultListener<List<String>>) {
        CoroutineScope( Dispatchers.IO).launch {
            try {
                val httpConnect = HttpConnectAPI.connectHttpAPI(uri)
                val data =  httpConnect.inputStream.bufferedReader().readText()
                withContext(Dispatchers.Default){
                    val json = JSONObject(data)
                    val jsonArray = json.optJSONArray(NAME_OBJECT_JSON_ARRAY_AUTHOR)
                    val listAuthor = mutableListOf<String>()
                    for (i in 0 until jsonArray.length()){
                        val jsonQuotes = jsonArray.optJSONObject(i)
                        val srtAuthor = jsonQuotes.optString(ApiConstance.AUTHOR_NAME)
                        listAuthor.add(srtAuthor)
                    }
                    withContext(Dispatchers.Main){listener.onSuccess(listAuthor)}
                }
            }catch (e : Exception){
                listener.onError(e)
                this.cancel()
            }
        }
    }

    fun paserJsonAPIToListTag (uri: Uri, listener: OnRemoteResultListener<List<String>>) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val httpConnect = HttpConnectAPI.connectHttpAPI(uri)
                val data =  httpConnect.inputStream.bufferedReader().readText()
                withContext(Dispatchers.Default){
                    val json = JSONObject(data)
                    val jsonArray = json.optJSONArray(NAME_OBJECT_JSON_ARRAY_TAG)
                    val listTag = mutableListOf<String>()
                    for (i in 0 until jsonArray.length()){
                        val jsonQuotes = jsonArray.optJSONObject(i)
                        val srtAuthor = jsonQuotes.optString(ApiConstance.TAG_NAME)
                        listTag.add(srtAuthor)
                    }
                    withContext(Dispatchers.Main){listener.onSuccess(listTag)}
                }
            }catch (e : Exception){
                listener.onError(e)
                this.cancel()
            }
        }
    }
}