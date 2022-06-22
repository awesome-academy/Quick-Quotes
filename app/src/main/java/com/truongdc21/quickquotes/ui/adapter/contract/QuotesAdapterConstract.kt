package com.truongdc21.quickquotes.ui.adapter.contract

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag

interface QuotesAdapterConstract {

    interface ClickItem {

        fun clickItemViewPlay(list: List<Quotes> , position: Int)

        fun clickItemCopy(srtQuotes : String)

        fun clickItemFavorite(quotes: Quotes)

        fun clickItemAuthor(srtAuthor : String)

        fun clickItemTag(srtTag : String)

        fun clickItemMore()

        fun clickRemoveFavorite(quotes: Quotes , mListQuotesLocal : List<Quotes>)

        fun clickItemFavoriteAuthor(srtAuthor: String)

        fun clickItemFavoriteTag(srtTag: String)
    }
}
