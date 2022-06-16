package com.truongdc21.quickquotes.ui.adapter.contract

import com.truongdc21.quickquotes.data.model.Search

interface SearchAdapterContract {

    interface ClickItem {

        fun clickQuotes(search: Search)

        fun clickAuthor(search: Search)

        fun clickTag(search: Search)

        fun clickHistory(text: String)

        fun removeHistory (search: Search)
    }
}