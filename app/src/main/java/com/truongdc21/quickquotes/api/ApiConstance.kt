package com.truongdc21.quickquotes.api

object ApiConstance {
    const val URL_QUOTES = "https://goquotes-api.herokuapp.com/api/v1/all/quotes"
    const val URL_AUTHOR = "https://goquotes-api.herokuapp.com/api/v1/all/authors"
    const val URL_TAG = "https://goquotes-api.herokuapp.com/api/v1/all/tags"
    const val URL_QUOTES_RANDOM = "https://goquotes-api.herokuapp.com/api/v1/random?"
    const val URL_AUTHOR_TAG = "https://goquotes-api.herokuapp.com/api/v1/all?"

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
}
