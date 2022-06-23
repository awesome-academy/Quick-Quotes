package com.truongdc21.quickquotes.presenter.fragmentHome

import android.content.Context
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.repository.AuthorRepository
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.repository.TagRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener
import com.truongdc21.quickquotes.utils.isNetworkAvailable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentPresenter(
    private val mContext: Context,
    private val mRepoQuotes: QuotesRepository,
    private val mRepoAuthor: AuthorRepository,
    private val mRepoTag: TagRepository,
) : HomeFragmentContact.Presenter {

    private var mView: HomeFragmentContact.View? = null

    override fun onStart() {
        getListQuotesAPI()
    }

    override fun onStop() {

    }

    override fun setView(view: HomeFragmentContact.View?) {
        this.mView = view
    }

    override fun getListQuotesAPI() {
        if (!mContext.isNetworkAvailable()){
            mView?.showToast(mContext.resources.getString(R.string.internet_disconnect))
            return
        }
        mView?.loadingWait()
        readQuotesDB()
        mRepoQuotes.getApiQuotesList(object : OnRemoteResultListener<List<Quotes>> {
            override fun onSuccess(data: List<Quotes>) {
                mView?.loadingDone()
                mView?.setAdapter(data)
            }

            override fun onError(exception: Exception?) {
                mView?.loadingDone()
            }
        })
    }

    override fun insertQuotesDB(quotes: Quotes) {
        mRepoQuotes.insertQuotes(quotes, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.showToast(mContext.resources.getString(R.string.addFavoriteSuccesss))
                readQuotesDB()
            }

            override fun onError(exception: java.lang.Exception?) {
                exception?.let {
                    mView?.showToast(mContext.resources.getString(R.string.addFavoriteFail))
                }
            }
        })
    }

    override fun readQuotesDB() {
        CoroutineScope(Dispatchers.IO).launch{
            mRepoQuotes.readQuotes(object : OnLocalResultListener<List<Quotes>> {
                override fun onSuccess(data: List<Quotes>) {
                    mView?.showAdapterListLocal(data)
                }

                override fun onError(exception: java.lang.Exception?) {
                    // TODO implement later
                }
            })
        }
    }

    override fun deleteQuotesDB(id: Int) {
        mRepoQuotes.deleteQuotes(id, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.deleteFavoriteSuccesss()
            }

            override fun onError(exception: java.lang.Exception?) {
                exception?.let { mView?.deleteFavoriteFail(it) }
            }
        })
    }

    override fun getListAuthor() {
        mRepoAuthor.readAuthor(object : OnLocalResultListener<List<Author>> {
            override fun onSuccess(data: List<Author>) {
                mView?.setDataAuthor(data)
            }

            override fun onError(exception: java.lang.Exception?) {
                // TODO implement later
            }
        })
    }

    override fun getListTag() {
        mRepoTag.readTag(object : OnLocalResultListener<List<Tag>> {
            override fun onSuccess(data: List<Tag>) {
                mView?.setDataTag(data)
            }

            override fun onError(exception: java.lang.Exception?) {
                // TODO implement later
            }
        })
    }

    override fun checkFavoriteAuthor(srtAuthor: String) {
        mRepoAuthor.readAuthor(object : OnLocalResultListener<List<Author>> {
            override fun onSuccess(data: List<Author>) {
                for (i in data) {
                    if (srtAuthor == i.mAuthor) {
                        deleteAuthor(i.id)
                        getListAuthor()
                        return
                    }
                }
                insertAuthor(srtAuthor)
                getListAuthor()
            }
            override fun onError(exception: java.lang.Exception?) {
                // TODO implement later
            }
        })
    }

    override fun checkFavoriteTag(srtTag: String) {
        mRepoTag.readTag( object : OnLocalResultListener<List<Tag>> {
            override fun onSuccess(data: List<Tag>) {
                for (i in data) {
                    if (srtTag == i.mTag) {
                        deleteTag(i.id)
                        getListTag()
                        return
                    }
                }
                insertTag(srtTag)
                getListTag()
            }
            override fun onError(exception: java.lang.Exception?) {
                // TODO implement later
            }
        })
    }

    override fun insertAuthor(srtAuthor: String) {
        mRepoAuthor.insertAuthor(srtAuthor, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.showToast(mContext.resources.getString(R.string.addFavoriteSuccesss))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.showToast(mContext.resources.getString(R.string.addFavoriteFail))
            }
        })
    }

    override fun insertTag(srtTag: String) {
        mRepoTag.insertTag(srtTag, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.showToast(mContext.resources.getString(R.string.addFavoriteSuccesss))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.showToast(mContext.resources.getString(R.string.addFavoriteFail))
            }
        })
    }

    override fun deleteAuthor(id: Int) {
        mRepoAuthor.deleteAuthor(id, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.showToast(mContext.resources.getString(R.string.remove_success))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.showToast(mContext.resources.getString(R.string.remove_error))
            }
        })
    }

    override fun deleteTag(id: Int) {
        mRepoTag.deleteTag(id, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.showToast(mContext.resources.getString(R.string.remove_success))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.showToast(mContext.resources.getString(R.string.remove_error))
            }
        })
    }
}
