package com.truongdc21.quickquotes.Presenter.ActivityViewPlay

import android.content.Context
import com.google.common.truth.Truth
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.repository.AuthorRepository
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.repository.TagRepository
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.presenter.MainActivity.MainActivityContract
import com.truongdc21.quickquotes.presenter.MainActivity.MainActivityPresenter
import com.truongdc21.quickquotes.presenter.activityViewPlay.ViewPlayActivityContract
import com.truongdc21.quickquotes.presenter.activityViewPlay.ViewPlayActivityPresenter
import com.truongdc21.quickquotes.utils.isNetworkAvailable
import io.mockk.*
import com.google.common.truth.Truth.assertThat
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.Constant.ID
import com.truongdc21.quickquotes.utils.Constant.TEST
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK

import org.junit.Before
import org.junit.Test

class ViewPlayActivityPresenterTest {

    @RelaxedMockK
    private lateinit var mView: ViewPlayActivityContract.View

    @RelaxedMockK
    private lateinit var mContext: Context

    @MockK
    private lateinit var mRepoQuotes: QuotesRepository

    @MockK
    private lateinit var mRepoAuthor: AuthorRepository

    @MockK
    private lateinit var mRepoTag: TagRepository

    private var mListQuotes = mutableListOf(Quotes(0, TEST, TEST, TEST, TEST))
    private var mListNumberTrabslate = mutableListOf<Int>()
    private var mListAuthorLocal = mutableListOf<Author>()
    private var mListTagLocal = mutableListOf<Tag>()
    private var mPosition: Int? = null
    val quotes = Quotes(ID , TEST , TEST , TEST , TEST)

    private lateinit var mPresenter: ViewPlayActivityPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        mPresenter = spyk(
            ViewPlayActivityPresenter(
                mContext,
                mRepoQuotes,
                mRepoAuthor, mRepoTag
            )
        )
        mPresenter.setView(mView)
    }

    @Test
    fun `onStart`() {

    }

    @Test
    fun `onStop`() {
    }

    @Test
    fun `set View Succes`() {
        mPresenter.setView(mView)
        Truth.assertThat(mView).isNotNull()
    }

    @Test
    fun `set View Fail `() {
        val mPresenterNull: MainActivityPresenter? = null
        val view: MainActivityContract.View? = null
        mPresenterNull?.setView(view)
        Truth.assertThat(view).isNull()
    }

    @Test
    fun `getListQuotesAPIRandom Success`() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val data = mutableListOf<Quotes>()
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
        mPresenter.getListQuotesAPIRandom()
        verify {
            mView.showAdapterAPIRandom(data)
        }
    }

    @Test
    fun `getListQuotesAPIRandom Fail `() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        every {
            mContext.isNetworkAvailable()
        } answers {
            true
        }
        every {
            mRepoQuotes.getApiQuotesList(capture(listener))
        } answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListQuotesAPIRandom()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `getListQuotesApiReload Success`() {
        every {
            !mContext.isNetworkAvailable()
        } answers {
            false
        }
        mPresenter.getListQuotesApiReload()
    }

    @Test
    fun `getListQuotesApiReload Fail`() {
        every {
            !mContext.isNetworkAvailable()
        } answers {
            false
        }
        mPresenter.getListQuotesApiReload()
    }

    @Test
    fun `check Item Favorite onSuccess`() {
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val data = mutableListOf<Quotes>()
        val position = Constant.POSITION_ZERO
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.checkItemFavorite(data , position)
        assertThat(data).isNotNull()
    }

    @Test
    fun `check Item Favorite onFail`() {
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        val data = mutableListOf<Quotes>()
        val position = Constant.POSITION_ZERO
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.checkItemFavorite(data , position)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `click Item Favorite onSuccess`() {
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val data = mutableListOf<Quotes>()
        val position = Constant.POSITION_ZERO
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onSuccess(data)

        }
        every {
            mPresenter.mListQuotes[position]
        } answers {
            quotes
        }
        mPresenter.clickItemFavorite(data , position)
        assertThat(data).isNotNull()
    }

    @Test
    fun `click Item Favorite onFail`() {
        val listener = slot<OnLocalResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        val data = mutableListOf<Quotes>()
        val position = Constant.POSITION_ZERO
        every {
            mRepoQuotes.readQuotes(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        every {
            mPresenter.mListQuotes[position]
        } answers {
            quotes
        }
        mPresenter.clickItemFavorite(data , position)
        assertThat(exception).isNotNull()
    }


    @Test
    fun `insert Favorite onSuccess`() {
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val quotes = Quotes(ID , TEST , TEST , TEST , TEST)
        every {
            mRepoQuotes.insertQuotes(quotes , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.insertFavorite(quotes)
        assertThat(data).isNotNull()
    }

    @Test
    fun `insert Favorite onFail`() {
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val quotes = Quotes(ID , TEST , TEST , TEST , TEST)
        every {
            mRepoQuotes.insertQuotes(quotes , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.insertFavorite(quotes)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `remove Favorite onSuccess`() {
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val quotes = Quotes(0 , TEST , TEST , TEST , TEST)
        every {
            mRepoQuotes.deleteQuotes(quotes.id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.removeFavorite(quotes.id)
        assertThat(data).isNotNull()
    }

    @Test
    fun `remove Favorite onFail`() {
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val quotes = Quotes(ID , TEST , TEST , TEST , TEST)
        every {
            mRepoQuotes.deleteQuotes(quotes.id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.removeFavorite(quotes.id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `get List Author onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Author>>>()
        val data = mutableListOf<Author>()
        every {
            mRepoAuthor.readAuthor(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getDataAuthor()
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
        mPresenter.getDataAuthor()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `get List Tag onSuccess`(){
        val listener = slot<OnLocalResultListener<List<Tag>>>()
        val data = mutableListOf<Tag>()
        every {
            mRepoTag.readTag(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getDataTag()
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
        mPresenter.getDataTag()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert List Author onError`(){
        val listener = slot<OnLocalResultListener<Unit>>()
        val author= TEST
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
        val quotes = Quotes(2, "test" ,"test" , "test" , "url")
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
        mPresenter.checkFavoriteTag(srtTag)
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