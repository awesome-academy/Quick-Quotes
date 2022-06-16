package com.truongdc21.quickquotes.database.search


import com.truongdc21.quickquotes.data.model.Search

interface SearchDao {

    suspend fun insertSearch(search: Search)

    suspend fun updateSearch(search: Search, id : Int)

    suspend fun deleteSearch(id: Int)

    suspend fun readSearch(): List<Search>
}