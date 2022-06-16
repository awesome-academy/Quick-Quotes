package com.truongdc21.quickquotes.api

import android.net.Uri
object ApiGetURL {

    const val QUERY_TYPE = "type"
    const val QUERY_VAL = "val"
    const val QUERY_AUTHOR_KEY = "author"
    const val QUERY_TAG_KEY = "tag"

    fun getURLAPI(URL: String ): Uri {
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .build()
        return builtURI
    }

    fun getURLRandomQuotes(URL: String ): Uri{
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .appendQueryParameter(
                ApiConstance.QUERY_COUNT_RANDOM_QUOTES,
                ApiConstance.QUERY_KEY_RANDOM_QUOTES
            )
            .build()
        return builtURI
    }

    private fun getURLAuthor(URL: String , keyQuery : String): Uri{
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .appendQueryParameter(QUERY_TYPE, QUERY_AUTHOR_KEY)
            .appendQueryParameter(QUERY_VAL , keyQuery)
            .build()
        return builtURI
    }

    private fun getURLTag(URL: String , keyQuery : String): Uri{
        val builtURI: Uri = Uri.parse(URL).buildUpon()
            .appendQueryParameter(QUERY_TYPE, QUERY_TAG_KEY)
            .appendQueryParameter(QUERY_VAL, keyQuery)
            .build()
        return builtURI
    }
}
