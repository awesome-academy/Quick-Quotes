package com.truongdc21.quickquotes.ui.adapter

import com.truongdc21.quickquotes.data.model.Quotes

interface QuotesAdapterConstract {
    interface ClickItem {
        fun clickItemViewPlay(list: List<Quotes> , position: Int)
        fun clickItemCopy(srtQuotes : String)
        fun clickItemFavorite(quotes: Quotes)
        fun clickItemAuthor(srtAuthor : String)
        fun clickItemTag(srtTag : String)
        fun clickItemMore()
        fun clickRemoveFavorite(quotes: Quotes , mListQuotesLocal : List<Quotes>)
    }
}
