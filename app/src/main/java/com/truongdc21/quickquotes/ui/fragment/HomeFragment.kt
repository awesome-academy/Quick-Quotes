package com.truongdc21.quickquotes.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.databinding.FragmentHomeBinding
import com.truongdc21.quickquotes.presenter.fragmentHome.HomeFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentHome.HomeFragmentPresenter
import com.truongdc21.quickquotes.ui.activity.AuthorActivity
import com.truongdc21.quickquotes.ui.activity.ViewPlayActivity
import com.truongdc21.quickquotes.ui.adapter.QuotesAdapter
import com.truongdc21.quickquotes.ui.adapter.contract.QuotesAdapterConstract
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.copyToClipboard
import com.truongdc21.quickquotes.utils.showToast
import com.truongdc21.quickquotes.utils.*
import kotlinx.coroutines.*
import java.lang.Exception

class HomeFragment :
        BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
        HomeFragmentContact.View,
        QuotesAdapterConstract.ClickItem {

    private  var mPresenter : HomeFragmentPresenter? = null
    private var mProgressDialog : ProgressDialog? = null
    private val adapterQuotes by lazy { QuotesAdapter(this@HomeFragment.requireContext()) }
    private val job = Job()

    override fun initViews() {
    }

    override fun initData()  {
        mPresenter = HomeFragmentPresenter(
            this@HomeFragment.requireContext(),
            InitRepository.initRepositoryQuotes(this@HomeFragment.requireContext()),
            InitRepository.initRepositoryAuthor(this@HomeFragment.requireContext()),
            InitRepository.initRepositoryTag(this@HomeFragment.requireContext())
            )
        mPresenter?.setView(this)
        mPresenter?.onStart()
    }

    override fun setAdapter(mListQuotes: List<Quotes> ) {
        CoroutineScope( job + Dispatchers.Main).launch {
            adapterQuotes.setListQuotes(
                mListQuotes,
                this@HomeFragment
            )
            binding.apply {
                rvScreenHome.layoutManager = LinearLayoutManager(this@HomeFragment.context)
                rvScreenHome.adapter = adapterQuotes
            }
        }
    }

    override fun loadingWait() {
        mProgressDialog = ProgressDialog(this@HomeFragment.context)
        mProgressDialog?.let {
            it.setCancelable(false)
            it.show()
            it.setContentView(R.layout.layout_custom_propressdialog)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
    }

    override fun loadingDone() {
        mProgressDialog?.dismiss()
    }

    override fun showAdapterListLocal(mListQuotesLocal: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.Main){
            binding.apply {
                adapterQuotes.setListQuotesLocal(mListQuotesLocal)
            }
        }
    }

    override fun deleteFavoriteSuccesss() {
        lifecycleScope.launch(Dispatchers.Main){
            this@HomeFragment.requireContext().apply {
                showToast(resources.getString(R.string.deleteFavoriteSuccesss))
            }
        }
    }

    override fun deleteFavoriteFail(exception: Exception) {
        lifecycleScope.launch(Dispatchers.Main){
            this@HomeFragment.requireContext().apply {
                showToast(resources.getString(R.string.deleteFavoriteFail))
            }
        }
    }

    override fun setDataAuthor(mList: List<Author>) {
        lifecycleScope.launch(Dispatchers.IO){
            adapterQuotes.setListAuthorLocal(mList)
        }
    }

    override fun setDataTag(mList: List<Tag>) {
        lifecycleScope.launch(Dispatchers.IO){
            adapterQuotes.setListTagLocal(mList)
        }
    }

    override fun showToast(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@HomeFragment.context?.showToast(message)
        }
    }


    override fun clickItemViewPlay(list: List<Quotes>, position: Int) {
        lifecycleScope.launch(Dispatchers.Main){
            this@HomeFragment.context?.switchViewPlayActivity(list , position)
        }
    }

    override fun clickItemCopy(srtQuotes: String) {
        context?.copyToClipboard(srtQuotes)
    }

    override fun clickItemFavorite(quotes: Quotes) {
        lifecycleScope.launch(Dispatchers.IO){
            mPresenter?.insertQuotesDB(quotes)
            mPresenter?.readQuotesDB()
        }
    }

    override fun clickItemAuthor(srtAuthor: String) {
        val intent = Intent(this@HomeFragment.context , AuthorActivity::class.java)
        intent.putExtra(Constant.INTEN_AUTHOR, srtAuthor)
        startActivity(intent)
    }

    override fun clickItemTag(srtTag: String) {
        this@HomeFragment.context?.switchTagActivity(srtTag)
    }

    override fun clickItemMore() {
        mPresenter?.getListAuthor()
        mPresenter?.getListTag()
    }

    override fun clickRemoveFavorite(quotes: Quotes , mListQuotesLocal: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.IO){
            for (i in mListQuotesLocal){
                if (quotes.mQuotes == i.mQuotes){
                    mPresenter?.deleteQuotesDB(i.id)
                }
            }
            mPresenter?.readQuotesDB()
        }
    }

    override fun clickItemFavoriteAuthor(srtAuthor: String) {
        mPresenter?.checkFavoriteAuthor(srtAuthor)
    }

    override fun clickItemFavoriteTag(srtTag: String) {
        mPresenter?.checkFavoriteTag(srtTag)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
