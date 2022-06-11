package com.truongdc21.quickquotes.database.quotes

import com.truongdc21.quickquotes.data.model.Quotes

interface QuotesDaoDb {

    suspend fun insertQuotes(quotes: Quotes)

    suspend fun updateQuotes(quotes: Quotes, id : Int)

    suspend fun deleteQuotes(id:Int)

    suspend fun readQuotes(): List<Quotes>
}
