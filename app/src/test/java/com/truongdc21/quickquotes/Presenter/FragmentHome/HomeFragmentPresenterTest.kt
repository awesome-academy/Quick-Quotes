package com.truongdc21.quickquotes.Presenter.FragmentHome

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.repository.AuthorRepository
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.repository.TagRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.presenter.activityAuthor.AuthorActivityContract
import com.truongdc21.quickquotes.presenter.fragmentHome.HomeFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentHome.HomeFragmentPresenter
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.isNetworkAvailable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK

import org.junit.Before
import org.junit.Test

class HomeFragmentPresenterTest {

    @RelaxedMockK
    private lateinit var mView : HomeFragmentContact.View

    @RelaxedMockK
    private lateinit var mContext: Context

    @MockK
    private lateinit var mRepoQuotes : QuotesRepository

    @MockK
    private lateinit var mRepoAuthor : AuthorRepository

    @MockK
    private lateinit var mRepoTag : TagRepository

    private lateinit var mPresenter : HomeFragmentContact.Presenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this , relaxed = true)
        mPresenter = spyk(
            HomeFragmentPresenter(
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
        val view : HomeFragmentContact.View? = null
        assertThat(view).isNull()
    }


    @Test
    fun `getListQuotesAPIRandom Success`() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val data  = mutableListOf<Quotes>()
        every {
            mContext.isNetworkAvailable()
        } answers {
            true
        }
        every {
            mRepoQuotes.getApiQuotesList(capture(listener))
        } answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListQuotesAPI()
        verify {
            mView.setAdapter(data)
        }
    }

    @Test
    fun `getListQuotesAPIRandom Fail `() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val exception  = mockk<Exception>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoQuotes.getApiQuotesList(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListQuotesAPI()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert Quotes Db Success`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val quotes = mockk<Quotes>()
        every {
            mRepoQuotes.insertQuotes(quotes , capture(listener) )
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.insertQuotesDB(quotes)
        assertThat(data).isNotNull()
    }

    @Test
    fun `insert Quotes Db onError`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val quotes = mockk<Quotes>()
        every {
            mRepoQuotes.insertQuotes(quotes , capture(listener) )
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.insertQuotesDB(quotes)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `read Quotes Db onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val data = mockk<List<Quotes>>()
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.readQuotesDB()
        assertThat(data).isNotNull()
    }

    @Test
    fun `read Quotes Db onError`(){
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.readQuotesDB()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `delete Quotes Db onSuccess`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoQuotes.deleteQuotes(id, capture(listener) )
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.deleteQuotesDB(id)
        assertThat(data).isNotNull()
    }

    @Test
    fun `delete Quotes Db onFail`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val id = Constant.POSITION_ZERO
        every {
            mRepoQuotes.deleteQuotes(id, capture(listener) )
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.deleteQuotesDB(id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `get List Author onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val data = mockk<List<Author>>()
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListAuthor()
        assertThat(data).isNotNull()
    }

    @Test
    fun `get List Author onError`(){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val exception = mockk<Exception>()
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListAuthor()
        assertThat(exception).isNotNull()

    }

    @Test
    fun `get List Tag onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val data = mockk<List<Tag>>()
        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListTag()
        assertThat(data).isNotNull()
    }

    @Test
    fun `get List Tag onError`(){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val exception = mockk<Exception>()
        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListTag()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert List Author onError`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val author= "test"
        val exception = mockk<Exception>()
        every {
            mRepoAuthor.insertAuthor(author , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.insertAuthor(author)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert List Author onSuccess`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val author= "test"
        every {
            mRepoAuthor.insertAuthor(author , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.insertAuthor(author)
        assertThat(data).isNotNull()
    }

    @Test
    fun `delete List Author onSuccess`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val quotes = Quotes(2, "test" ,"test" , "test" , "url")
        val mlist = mutableListOf(quotes)
        val exception = mockk<Exception>()
        every {
            mRepoAuthor.deleteAuthor(quotes.id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.deleteAuthor(quotes.id )
        assertThat(data).isNotNull()
    }

    @Test
    fun `delete List Author onError`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val quotes = Quotes(2, "test" ,"test" , "test" , "url")
        val mlist = mutableListOf(quotes)
        val exception = mockk<Exception>()
        every {
            mRepoAuthor.deleteAuthor(quotes.id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.deleteAuthor(quotes.id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert List Tag onError`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val tag= "test"
        val exception = mockk<Exception>()
        every {
            mRepoTag.insertTag(tag , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.insertTag(tag)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert List Tag onSuccess`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val tag= "test"
        val exception = mockk<Exception>()
        every {
            mRepoTag.insertTag(tag , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.insertTag(tag)
        assertThat(data).isNotNull()
    }


    @Test
    fun `delete List Tag onSuccess`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val quotes = Quotes(2, "test" ,"test" , "test" , "url")
        every {
            mRepoTag.deleteTag(quotes.id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.deleteTag(quotes.id)
        assertThat(data).isNotNull()
    }

    @Test
    fun `delete List Tag onError`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val quotes = Quotes(2, "test" ,"test" , "test" , "url")
        val mlist = mutableListOf(quotes)
        val exception = mockk<Exception>()
        every {
            mRepoTag.deleteTag(quotes.id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.deleteTag(quotes.id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `check Favorite Author onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val data = mutableListOf<Author>()
        val srtAuthor = Constant.AUTHOR
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.checkFavoriteAuthor(srtAuthor)
        assertThat(data).isNotNull()
    }

    @Test
    fun `check Favorite Author onError`(){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val srtTag = Constant.TAG
        val exception = mockk<Exception>()
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.checkFavoriteAuthor(srtTag)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `check Favorite Tag onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val srtTag = Constant.TAG
        val data = mutableListOf<Tag>()

        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.checkFavoriteTag(srtTag)
        assertThat(data).isNotNull()
    }

    @Test
    fun `check Favorite Tag onError`(){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val srtTag = Constant.TAG
        val exception = mockk<Exception>()
        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.checkFavoriteTag(srtTag)
        assertThat(exception).isNotNull()
    }
}