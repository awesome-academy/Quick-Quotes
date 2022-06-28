package com.truongdc21.quickquotes.Presenter.MainActivity

import com.google.common.truth.Truth.assertThat
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.presenter.MainActivity.MainActivityContract
import com.truongdc21.quickquotes.presenter.MainActivity.MainActivityPresenter
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.Constant.ID
import com.truongdc21.quickquotes.utils.Constant.TEST
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

class MainActivityPresenterTest {

    @RelaxedMockK
    private lateinit var mView : MainActivityContract.View

    @MockK
    private lateinit var mRepoQuotes : QuotesRepository

    private lateinit var mPresenter : MainActivityPresenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this , relaxed = true)
        mPresenter = spyk(MainActivityPresenter(mRepoQuotes))
        mPresenter.setView(mView)
    }

    @Test
    fun `test onStart Fail`() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val key = Constant.KEY_MORNING
        val exception = mockk<Exception>()
        every {
            mRepoQuotes.getApiQuotesWithTag(key , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
        mPresenter.onStart()
        assertThat(exception).isNotNull()
    }

    @Test
    fun `test onStart Success`() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val key = Constant.AUTHOR
        val data = mockk<List<Quotes>>()
        every {
            mRepoQuotes.getApiQuotesWithTag(key , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.onStart()
        assertThat(data).isNotNull()
    }


    @Test
    fun `set View Success`() {
        mPresenter.setView(mView)
        assertThat(mView).isNotNull()
    }

    @Test
    fun `set View Fail`(){
        val mPresenterNull : MainActivityPresenter? = null
        val view : MainActivityContract.View? = null
        mPresenter.setView(view)
        assertThat(view).isNull()
    }

    @Test
    fun `getApiList Success`() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val key = Constant.AUTHOR
        val data = mutableListOf(Quotes(ID , TEST , TEST , TEST , TEST))
        every {
            mRepoQuotes.getApiQuotesWithTag(key , capture(listener))
        }answers {
            listener.captured.onSuccess(data)
        }
        mPresenter.getApiList()
        assertThat(data).isNotNull()
    }

    @Test
    fun `getApiList Fail`() {
        val listener = slot<OnRemoteResultListener<List<Quotes>>>()
        val key = Constant.KEY_MORNING
        val exception = mockk<Exception>()
        every {
            mRepoQuotes.getApiQuotesWithTag(key , capture(listener))
        }answers {
            listener.captured.onError(exception)
        }
         mPresenter.getApiList()
        assertThat(exception).isNotNull()
    }

}