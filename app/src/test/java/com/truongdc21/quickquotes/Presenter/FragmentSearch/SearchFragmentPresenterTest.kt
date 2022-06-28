package com.truongdc21.quickquotes.Presenter.FragmentSearch

import android.content.Context
import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.repository.SearchRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.presenter.fragmentSearch.SearchFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentSearch.SearchFragmentPresenter
import com.truongdc21.quickquotes.utils.isNetworkAvailable
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK

import org.junit.Before
import org.junit.Test

class SearchFragmentPresenterTest {

    @RelaxedMockK
    private lateinit var mView : SearchFragmentContact.View

    @RelaxedMockK
    private lateinit var mContext : Context

    @MockK
    private lateinit var mRepoSearch : SearchRepository


    private lateinit var mPresenter : SearchFragmentPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this , relaxed = true)
        mPresenter = spyk(
            SearchFragmentPresenter(mContext, mRepoSearch)
        )
        mPresenter.setView(mView)
    }

    @Test
    fun `onStart Fail`() {
        every {
            !mContext.isNetworkAvailable()
        }answers {
            false
        }
        mPresenter.onStart()
        verify {
            mPresenter.getListSearchAPI()
            mPresenter.getListSearchHistory()
        }
    }

    @Test
    fun `onStart Success`() {
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        mPresenter.onStart()
        verify {
            mPresenter.getListSearchAPI()
            mPresenter.getListSearchHistory()
        }
    }

    @Test
    fun `set View Success`() {
        mPresenter.setView(mView)
        assertThat(mView).isNotNull()
    }

    @Test
    fun `set View Fail`(){
        val mPresenterNull : SearchFragmentPresenter? = null
        val view : SearchFragmentContact.View? = null
        mPresenterNull?.setView(view)
        assertThat(view).isNull()
    }


    @Test
    fun `check Internet Onsuccess`() {
        every {
            !mContext.isNetworkAvailable()
        }answers {
            false
        }
        mPresenter.getListSearchAPI()
        assertThat( mContext.isNetworkAvailable()).isFalse()
    }

    @Test
    fun `check Internet OnEror`() {
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        mPresenter.getListSearchAPI()
        assertThat( mContext.isNetworkAvailable()).isTrue()
    }

    @Test
    fun `check Key is Quotes is True`(){
        mPresenter.isQuotes = true
        mPresenter.isAuthor = true
        mPresenter.isTag = true
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        mPresenter.getListSearchAPI()
        verify {
            mView.showAdapterListAPI(mutableListOf<Search>())
        }
    }

    @Test
    fun `check Key is Quotes is Faile - listQuotes to Search -- onSuccess`(){
        mPresenter.isQuotes = false
        mPresenter.isAuthor = false
        mPresenter.isTag = false
        val listener  = slot<OnRemoteResultListener<List<Quotes>>>()
        val data = mockk<List<Quotes>>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListQuotes(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListSearchAPI()
        assertThat(data).isNotNull()
    }

    @Test
    fun `check Key is Quotes is Faile - listQuotes to Search - onError`(){
        mPresenter.isQuotes = false
        mPresenter.isAuthor = false
        mPresenter.isTag = false
        val listener  = slot<OnRemoteResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListQuotes(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListSearchAPI()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `check Key is Quotes is Faile - list Author to Search - onSuccess`(){
        mPresenter.isQuotes = false
        mPresenter.isAuthor = false
        mPresenter.isTag = false
        val listener  = slot<OnRemoteResultListener<List<String>>>()
        val data = mockk<List<String>>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListAuthor(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListSearchAPI()
        assertThat(data).isNotNull()
    }

    @Test
    fun `check Key is Quotes is Faile - list Author to Search - onFail`(){
        mPresenter.isQuotes = false
        mPresenter.isAuthor = false
        mPresenter.isTag = false
        val listener  = slot<OnRemoteResultListener<List<String>>>()
        val exception = mockk<Exception>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListAuthor(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListSearchAPI()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `check Key is Quotes is Faile - list Tag to Search - Success`(){
        mPresenter.isQuotes = false
        mPresenter.isAuthor = false
        mPresenter.isTag = false
        val listener  = slot<OnRemoteResultListener<List<String>>>()
        val data = mockk<List<String>>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListTag(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListSearchAPI()
        assertThat(data).isNotNull()
    }

    @Test
    fun `check Key is Quotes is Faile - list Tag to Search - Fail`(){
        mPresenter.isQuotes = false
        mPresenter.isAuthor = false
        mPresenter.isTag = false
        val listener  = slot<OnRemoteResultListener<List<String>>>()
        val exception = mockk<Exception>()
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListTag(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListSearchAPI()
        assertThat(exception).isNotNull()
    }


    @Test
    fun `get List Search History Success`() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val data = mockk<List<Search>>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getListSearchHistory()
        assertThat(data).isNotNull()
    }

    @Test
    fun `get List Search History Fail`() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val exception = mockk<Exception>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.getListSearchHistory()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert History - delete History - Success `() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val listDelete = slot<OnLocalResultListener<Unit>>()
        val data = mutableListOf<Search>()
        val dataDelete = mockk<Unit>()
        val search = mockk<Search>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        every {
            search.id?.let {
                mRepoSearch.deleteSearch(it , capture(listDelete))
            }

        }answers {
            listDelete.captured.onSuccess(dataDelete)
        }
        mPresenter.insertSearchHistory(search)
        assertThat(data).isNotNull()
        assertThat(dataDelete).isNotNull()
    }

    @Test
    fun `insert History - delete History - Fail `() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val listDelete = slot<OnLocalResultListener<Unit>>()
        val data = mutableListOf<Search>()
        val exceptionDelete = mockk<Exception>()
        val search = mockk<Search>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        every {
            search.id?.let {
                mRepoSearch.deleteSearch(it , capture(listDelete))
            }

        }answers {
            listDelete.captured.onError(exceptionDelete)
        }
        mPresenter.insertSearchHistory(search)
        assertThat(data).isNotNull()
        assertThat(exceptionDelete).isNotNull()
    }

    @Test
    fun `insert History - insert History - Success `() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val listDelete = slot<OnLocalResultListener<Unit>>()
        val data = mutableListOf<Search>()
        val dataDelete = mockk<Unit>()
        val search = mockk<Search>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        every {
            search.id?.let {
                mRepoSearch.insertSearch(search , capture(listDelete))
            }

        }answers {
            listDelete.captured.onSuccess(dataDelete)
        }
        mPresenter.insertSearchHistory(search)
        assertThat(data).isNotNull()
        assertThat(dataDelete).isNotNull()
    }

    @Test
    fun `insert History - insert History - Fail `() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val listDelete = slot<OnLocalResultListener<Unit>>()
        val data = mutableListOf<Search>()
        val exception = mockk<Exception>()
        val search = mockk<Search>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        every {
            search.id?.let {
                mRepoSearch.insertSearch(search , capture(listDelete))
            }

        }answers {
            listDelete.captured.onError(exception)
        }
        mPresenter.insertSearchHistory(search)
        assertThat(data).isNotNull()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `insert History - Read List Insert - Fail`() {
        val listener  = slot<OnLocalResultListener<List<Search>>>()
        val exception = mockk<Exception>()
        every {
            mRepoSearch.readSearch(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        assertThat(exception).isNotNull()
    }

    @Test
    fun `delete List Search onSuccess` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val data = mockk<Unit>()
        val id = 0
        every {
            mRepoSearch.deleteSearch(id , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.deleteSearchHistory(id)
        verify {
            mView.removeHistorySuccess()
        }
    }

    @Test
    fun `delete List Search onFail` (){
        val listener = slot<OnLocalResultListener<Unit>>()
        val exception = mockk<Exception>()
        val id = 0
        every {
            mRepoSearch.deleteSearch(id , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.deleteSearchHistory(id)
        assertThat(exception).isNotNull()
    }

    @Test
    fun `click Quotes Search - Check internet and laoding API success` (){
        val textSearch = "test"
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        mPresenter.clickQuotesSearch(textSearch)
        verify {
            mView.loadingApi()
        }

    }

    @Test
    fun `click Quotes Search - Check internet and laoding API Fail` (){
        val textSearch = "test"
        every{
            !mContext.isNetworkAvailable()
        } answers {
            false
        }
        mPresenter.clickQuotesSearch(textSearch)
    }

    @Test
    fun `click Quotes Search - check internet is true -- get List Quotes onSucces` (){
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val data = mutableListOf<Quotes>()
        val textSearch = "test"
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListQuotes(capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.clickQuotesSearch(textSearch)
        for (i in data){
            assertThat(textSearch).isEqualTo(i.mQuotes)
        }
    }

    @Test
    fun `click Quotes Search - check internet is get List Quotes onFail` (){
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val exception = mockk<Exception>()
        val textSearch = "test"
        every {
            mContext.isNetworkAvailable()
        }answers {
            true
        }
        every {
            mRepoSearch.getListQuotes(capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.clickQuotesSearch(textSearch)
        assertThat(exception).isNotNull()
    }
}