package com.truongdc21.quickquotes.presenter.activityViewPlay

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
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.isNetworkAvailable
import java.lang.Exception

class ViewPlayActivityPresenter(
    private val mContext: Context,
    private val mRepoQuotes : QuotesRepository,
    private val mRepoAuthor : AuthorRepository,
    private val mRepoTag : TagRepository,
    ):ViewPlayActivityContract.Presenter{

    private var mView : ViewPlayActivityContract.View? = null
    private var mListQuotes = mutableListOf<Quotes>()
    private var mListNumberTrabslate = mutableListOf<Int>()
    private var mListAuthorLocal = mutableListOf<Author>()
    private var mListTagLocal = mutableListOf<Tag>()
    private var mPosition : Int? = null

    override fun onStart() {}

    override fun onStop() {}

    override fun setView(view: ViewPlayActivityContract.View?) {
       this.mView = view
    }

    override fun getListQuotesAPIRandom() {

        if (!mContext.isNetworkAvailable()){
            mView?.onError(mContext.resources.getString(R.string.internet_disconnect))
            return
        }
        mView?.loadingPropressbar()
        mRepoQuotes.getApiQuotesList(object : OnRemoteResultListener<List<Quotes>> {
            override fun onSuccess(data: List<Quotes>) {
                mView?.loadingSuccess()
                mView?.showAdapterAPIRandom(data)
                mListQuotes.apply {
                    clear()
                    addAll(data)
                }
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun getListQuotesApiReload() {
        if (!mContext.isNetworkAvailable()){
            mView?.onError(mContext.resources.getString(R.string.internet_disconnect))
            return
        }
        mListNumberTrabslate.clear()
        getListQuotesAPIRandom()
        mView?.showItemFromApiReload(Constant.POSITION_ZERO)
        getPositionItemQuotes(Constant.POSITION_ZERO)
    }

    override fun getPositionItemQuotes(position: Int) {
        mPosition = position
        mView?.showItemQuotes(mListQuotes , position )
        checkTranslate(position)
    }

    override fun setDataListFromItent(mList: List<Quotes>, position: Int) {
        this.mListQuotes.apply {
            clear()
            addAll(mList)
        }

        mPosition = position
        mView?.showItemFromItent(mList , position)
    }

    override fun checkItemFavorite(mList: List<Quotes>, position: Int) {
        mRepoQuotes.readQuotes(object : OnLocalResultListener<List<Quotes>>{
            override fun onSuccess(data: List<Quotes>) {
                for (i in data){
                    if (mList[position].mQuotes == i.mQuotes){
                        mView?.checkFavorite(true)
                        break
                    }else {
                        mView?.checkFavorite(false)
                    }
                }
            }

            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun clickItemFavorite(mList: List<Quotes>, position: Int) {
        mRepoQuotes.readQuotes(object : OnLocalResultListener<List<Quotes>>{
            private val itemQuotes = mListQuotes[position]
            override fun onSuccess(data: List<Quotes>) {
                for (i in data){
                    if(itemQuotes.mQuotes == i.mQuotes){
                        removeFavorite(i.id)
                        checkItemFavorite(mList, position)
                        return
                    }
                }
                insertFavorite(itemQuotes)
                checkItemFavorite(mList, position)
            }
            override fun onError(exception: Exception?) {
                mView?.onError(mContext.resources.getString(R.string.get_list_error))
            }
        })
    }

    override fun insertFavorite(quotes: Quotes) {
        mRepoQuotes.insertQuotes(quotes, object : OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.addFavoriteSuccesss))
            }
            override fun onError(exception: Exception?) {
                mView?.onSuccess(mContext.resources.getString(R.string.addFavoriteFail))
            }
        })
    }

    override fun removeFavorite(id: Int) {
        mRepoQuotes.deleteQuotes(id, object : OnLocalResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.remove_success))
            }
            override fun onError(exception: Exception?) {
                mView?.onSuccess(mContext.resources.getString(R.string.remove_error))
            }
        })
    }

    override fun translateQuotes(position: Int) {
        var isCheck = true
        for (i in mListNumberTrabslate){
            if (i == position){
                mView?.translateQuotes(
                    false,
                    mListQuotes[position].mQuotes
                )
                mListNumberTrabslate.removeAt(mListNumberTrabslate.indexOf(position))
                isCheck = false
                break
            }
        }
        if (isCheck){
            mView?.translateQuotes(
                true,
               mListQuotes[position].mQuotes
            )
            mListNumberTrabslate.add(position)
        }
    }

    override fun getDataAuthor() {
        mRepoAuthor.readAuthor(object : OnLocalResultListener<List<Author>> {
            override fun onSuccess(data: List<Author>) {
                mListAuthorLocal.apply {
                    clear()
                    addAll(data)
                }
            }

            override fun onError(exception: java.lang.Exception?) {
                // TODO implement later
            }
        })
    }

    override fun getDataTag() {
        mRepoTag.readTag(object : OnLocalResultListener<List<Tag>> {
            override fun onSuccess(data: List<Tag>) {
                mListTagLocal.apply {
                    clear()
                    addAll(data)
                }
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
                        getDataAuthor()
                        return
                    }
                }
                insertAuthor(srtAuthor)
                getDataAuthor()
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
                        getDataTag()
                        return
                    }
                }
                insertTag(srtTag)
                getDataTag()
            }
            override fun onError(exception: java.lang.Exception?) {
                // TODO implement later
            }
        })
    }

    override fun insertAuthor(srtAuthor: String) {
        mRepoAuthor.insertAuthor(srtAuthor, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.addFavoriteSuccesss))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.onError(mContext.resources.getString(R.string.addFavoriteFail))
            }
        })
    }

    override fun deleteAuthor(id: Int) {
        mRepoAuthor.deleteAuthor(id, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.remove_success))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.onError(mContext.resources.getString(R.string.remove_error))
            }
        })
    }

    override fun insertTag(srtTag: String) {
        mRepoTag.insertTag(srtTag, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.addFavoriteSuccesss))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.onError(mContext.resources.getString(R.string.addFavoriteFail))
            }
        })
    }

    override fun deleteTag(id: Int) {
        mRepoTag.deleteTag(id, object : OnLocalResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView?.onSuccess(mContext.resources.getString(R.string.remove_success))
            }

            override fun onError(exception: java.lang.Exception?) {
                mView?.onError(mContext.resources.getString(R.string.remove_error))
            }
        })
    }

    override fun removeItemQuotes(position: Int) {
        mListQuotes.removeAt(position)
        mView?.showAdapterLaterRemove(mListQuotes)
        mView?.showItemQuotes(mListQuotes , position)
    }

    override fun checkItemFavoriteAuthotBottomSheet(srtAuthor: String): Boolean {
        var isCheck = true
        for (i in mListAuthorLocal){
            if (srtAuthor == i.mAuthor){
                isCheck = false
                break
            }
        }
        return isCheck
    }

    override fun checkItemFavoriteTagBottomSheet(srtTag: String): Boolean {
        var isCheck = true
        for (i in mListTagLocal){
            if (srtTag == i.mTag){
                isCheck = false
                break
            }
        }
        return isCheck
    }

    private fun checkTranslate(position: Int){
        var isCheck = true
        for (i in mListNumberTrabslate){
            if (position == i){
                mView?.translateQuotes(true , mListQuotes[position].mQuotes)
                isCheck = false
                break
            }
        }
        if (isCheck){
            mView?.translateQuotes(false , mListQuotes[position].mQuotes)
        }
    }
}
