package com.truongdc21.quickquotes.presenter.fragmentHome


import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import com.truongdc21.quickquotes.data.source.remote.OnRemoteResultListener

class HomeFragmentPresenter(
    private val mRepo: QuotesRepository,
) : HomeFragmentContact.Presenter{

    private var mView : HomeFragmentContact.View? = null

    override fun onStart() {
        getListQuotesAPI()
    }

    override fun onStop() {
    }

    override fun getListQuotesAPI() {
        mView?.loadingWait()
        readQuotesDB()
        mRepo.getApiQuotesList(object : OnRemoteResultListener<List<Quotes>> {
            override fun onSuccess(data: List<Quotes>) {
                mView?.loadingDone()
                mView?.setAdapter(data)
            }
            override fun onError(exception: Exception?) {
                mView?.loadingDone()
                mView?.onError()
            }
        })
    }

    override fun insertQuotesDB(quotes: Quotes) {
        mRepo.insertQuotes(quotes , object : OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                 mView?.insertFavoriteSuccesss()
            }

            override fun onError(exception: java.lang.Exception?) {
                exception?.let {
                    mView?.insertFavoriteFail(it)
                }
            }
        })
    }

    override fun readQuotesDB() {
        mRepo.readQuotes(object : OnLocalResultListener<List<Quotes>>{
            override fun onSuccess(data: List<Quotes>) {
                mView?.setAdapterListLocal(data)
            }
            override fun onError(exception: java.lang.Exception?) {

            }
        })
    }

    override fun deleteQuotesDB(id : Int) {
        mRepo.deleteQuotes(id , object : OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.deleteFavoriteSuccesss()
            }

            override fun onError(exception: java.lang.Exception?) {
                exception?.let { mView?.deleteFavoriteFail(it) }
            }
        })
    }

    override fun setView(view: HomeFragmentContact.View?) {
        this.mView = view
    }
}
