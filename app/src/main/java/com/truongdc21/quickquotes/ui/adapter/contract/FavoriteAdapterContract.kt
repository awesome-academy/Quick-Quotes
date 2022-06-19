package com.truongdc21.quickquotes.ui.adapter.contract

import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag

interface FavoriteAdapterContract {

    interface iClickRemove {

        fun clickRemoveQuotes(id : Int)

        fun clickRemoveAuthor(id: Int)

        fun clickRemveTag(id: Int)
    }

    interface iClickItem {

        fun clickItemQuotes(mList: List<Quotes>, position : Int)

        fun clickItemAuthor(author: Author)

        fun clickItemTag(tag: Tag)
    }
}
