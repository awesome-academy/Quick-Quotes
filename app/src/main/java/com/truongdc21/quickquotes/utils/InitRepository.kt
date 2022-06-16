package com.truongdc21.quickquotes.utils

import android.content.Context
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.repository.AuthorRepository
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.repository.TagRepository
import com.truongdc21.quickquotes.data.source.local.AuthorLocalSource
import com.truongdc21.quickquotes.data.source.local.QuotesLocalSource
import com.truongdc21.quickquotes.data.source.local.TagLocalSource
import com.truongdc21.quickquotes.data.source.remote.AuthorRemoteSource
import com.truongdc21.quickquotes.data.source.remote.QuotesRemoteSource
import com.truongdc21.quickquotes.data.source.remote.TagRemoteSource
import com.truongdc21.quickquotes.database.MyDatabaseHelper
import com.truongdc21.quickquotes.database.author.AuthorDbImpl
import com.truongdc21.quickquotes.database.quotes.QuotesDbImpl
import com.truongdc21.quickquotes.database.tag.TagDbImpl

object InitRepository {

    fun initRepositoryQuotes(context: Context) : QuotesRepository {
        return QuotesRepository.getInstace(
            QuotesRemoteSource.getInstance(),
            QuotesLocalSource.getInstance(
                QuotesDbImpl.getInstance(
                    MyDatabaseHelper.getInstance(context)
                )
            )
        )
    }

    fun initRepositoryAuthor(context: Context) : AuthorRepository {
        return AuthorRepository.getInstace(
            AuthorRemoteSource.getInstance(),
            AuthorLocalSource.getInstance(
                AuthorDbImpl.getInstance(
                    MyDatabaseHelper.getInstance(context)
                )
            )
        )
    }

    fun initRepositoryTag(context: Context) : TagRepository {
        return TagRepository.getInstace(
            TagRemoteSource.getInstance(),
            TagLocalSource.getInstance(
                TagDbImpl.getInstance(
                    MyDatabaseHelper.getInstance(context)
                )
            )
        )
    }
}
