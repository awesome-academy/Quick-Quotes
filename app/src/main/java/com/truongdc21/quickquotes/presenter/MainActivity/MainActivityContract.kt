package com.truongdc21.quickquotes.presenter.MainActivity

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.presenter.activityAuthor.AuthorActivityContract
import com.truongdc21.quickquotes.utils.base.BasePresenter

interface MainActivityContract {

    interface Persenter : BasePresenter<View> {
        fun getApiList()
    }

    interface View {
        fun sendQuotesNotification(quotes: Quotes)
    }
}
