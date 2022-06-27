package com.truongdc21.quickquotes.Presenter.FragmentFavorite

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.repository.AuthorRepository
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.repository.TagRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.presenter.activityAuthor.AuthorActivityContract
import com.truongdc21.quickquotes.presenter.fragmentFavorite.FavoriteFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentFavorite.FavoriteFragmentPresenter
import com.truongdc21.quickquotes.utils.Constant
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK

import org.junit.Before
import org.junit.Test

class FavoriteFragmentPresenterTest {

    @RelaxedMockK
    private lateinit var mView : FavoriteFragmentContact.View

    @RelaxedMockK
    private lateinit var mContext: Context

    @MockK
    private lateinit var mRepoQuotes : QuotesRepository

    @MockK
    private lateinit var mRepoAuthor : AuthorRepository

    @MockK
    private lateinit var mRepoTag : TagRepository

    private lateinit var mPresenter : FavoriteFragmentContact.Presenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this , relaxed = true)
        mPresenter = spyk(
            FavoriteFragmentPresenter(
            mContext,
            mRepoQuotes,
            mRepoAuthor,
            mRepoTag
        )
        )
        mPresenter.setView(mView)
    }

    @Test
    fun `set View onSuccess`(){
        val view = mView
        mPresenter.setView(view)
        assertThat(view).isNotNull()
    }

    @Test
    fun `set View onError`(){
        val view : AuthorActivityContract? = null
        assertThat(view).isNull()
    }

    @Test
    fun `get List Quotes Favorite onSuccess` (){
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val data = mutableListOf<Quotes>()
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListQuotesFavorite()
        assertThat(data).isNotNull()
    }

    @Test
    fun `get List Quotes Favorite Fail` (){
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListQuotesFavorite()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `get List Author Favorite onSuccess` (){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val data = mutableListOf<Author>()
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListAuthorFavorite()
        assertThat(data).isNotNull()
    }

    @Test
    fun `get List Author Favorite Fail` (){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val exception = mockk<Exception>()
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListAuthorFavorite()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `get List Tag Favorite onSuccess` (){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val data = mockk<List<Tag>>()
        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListAuthorFavorite()
        assertThat(data).isNotNull()
    }

    @Test
    fun `get List Tag Favorite Fail` (){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val exception = mockk<Exception>()
        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListTagFavorite()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `remove Quotes Favorite Fail` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoQuotes.deleteQuotes( id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.removeQuotes(id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `remove Quotes Favorite Success` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoQuotes.deleteQuotes( id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.removeQuotes(id)
        assertThat(data).isNotNull()
    }

    @Test
    fun `remove Author Favorite Fail` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoAuthor.deleteAuthor( id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.removeAuthor(id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `remove Author Favorite Success` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoAuthor.deleteAuthor( id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.removeAuthor(id)
        assertThat(data).isNotNull()
    }

    @Test
    fun `remove Tag Favorite Fail` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoTag.deleteTag( id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.removeTag(id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `remove Tag Favorite Success` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoTag.deleteTag( id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.removeTag(id)
        assertThat(data).isNotNull()
    }
}