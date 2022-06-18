package com.truongdc21.quickquotes.presenter.fragmentFavorite

import android.content.Context
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.data.repository.AuthorRepository
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.repository.TagRepository
import com.truongdc21.quickquotes.data.source.local.OnLocalResultListener
import java.lang.Exception

class FavoriteFragmentPresenter (
    private val mContext: Context,
    private val mRepoQuotes : QuotesRepository,
    private val mRepoAuthor : AuthorRepository,
    private val mRepoTag : TagRepository
    ):FavoriteFragmentContact.Presenter {

    private var mView : FavoriteFragmentContact.View? = null

    override fun onStart() {}

    override fun onStop() {}

    override fun setView(view: FavoriteFragmentContact.View?) {
        this.mView = view
    }

    override fun getListQuotesFavorite() {
        mRepoQuotes.readQuotes(object : OnLocalResultListener<List<Quotes>>{
            override fun onSuccess(data: List<Quotes>) {
                mView?.showAdapterQuotes(data)
                if (data.isEmpty()) mView?.onError(mContext.resources.getString(R.string.list_isempty))
            }
            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun getListAuthorFavorite() {
        mRepoAuthor.readAuthor(object : OnLocalResultListener<List<Author>>{
            override fun onSuccess(data: List<Author>) {
                mView?.showAdapterAuthor(data)
                if (data.isEmpty()) mView?.onError(mContext.resources.getString(R.string.list_isempty))
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun getListTagFavorite() {
       mRepoTag.readTag(object : OnLocalResultListener<List<Tag>>{
           override fun onSuccess(data: List<Tag>) {
               mView?.showAdapterTag(data)
               if (data.isEmpty()) mView?.onError(mContext.resources.getString(R.string.list_isempty))
           }

           override fun onError(exception: Exception?) {
               mView?.onError(mContext.resources.getString(R.string.get_list_error))
           }
       })
    }

    override fun removeQuotes(id: Int) {
        mRepoQuotes.deleteQuotes(id, object :OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.removeSuccess(mContext.resources.getString(R.string.remove_success))
                this@FavoriteFragmentPresenter.getListQuotesFavorite()
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.remove_error))
            }
        })
    }

    override fun removeAuthor(id: Int) {
       mRepoAuthor.deleteAuthor(id, object: OnLocalResultListener<Unit>{
           override fun onSuccess(data: Unit) {
               mView?.removeSuccess(mContext.resources.getString(R.string.remove_success))
               this@FavoriteFragmentPresenter.getListAuthorFavorite()
           }

           override fun onError(exception: Exception?) {
               mView?.onError(mContext.resources.getString(R.string.remove_error))
           }
       })
    }

    override fun removeTag(id: Int) {
        mRepoTag.deleteTag(id, object: OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.removeSuccess(mContext.resources.getString(R.string.remove_success))
                this@FavoriteFragmentPresenter.getListTagFavorite()
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.remove_error))
            }
        })
    }
}
