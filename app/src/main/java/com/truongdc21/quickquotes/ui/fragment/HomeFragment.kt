package com.truongdc21.quickquotes.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.repository.QuotesRepository
import com.truongdc21.quickquotes.data.source.local.QuotesLocalSource
import com.truongdc21.quickquotes.data.source.remote.QuotesRemoteSource
import com.truongdc21.quickquotes.database.MyDatabaseHelper
import com.truongdc21.quickquotes.database.quotes.QuotesDbImpl
import com.truongdc21.quickquotes.databinding.FragmentHomeBinding
import com.truongdc21.quickquotes.presenter.fragmentHome.HomeFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentHome.HomeFragmentPresenter
import com.truongdc21.quickquotes.ui.activity.AuthorActivity
import com.truongdc21.quickquotes.ui.activity.TagActivity
import com.truongdc21.quickquotes.ui.activity.ViewPlayActivity
import com.truongdc21.quickquotes.ui.adapter.QuotesAdapter
import com.truongdc21.quickquotes.ui.adapter.contract.QuotesAdapterConstract
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.copyToClipboard
import com.truongdc21.quickquotes.utils.showToast
import com.truongdc21.quickquotes.utils.switchActivity
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
            QuotesRepository.getInstace(
                QuotesRemoteSource.getInstance(),
                QuotesLocalSource.getInstance(
                    QuotesDbImpl.getInstance(
                        MyDatabaseHelper.getInstance(this@HomeFragment.requireContext())
                    )
                )
            )
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

    override fun onError() {
    }

    override fun loadingWait() {
        mProgressDialog = ProgressDialog(this@HomeFragment.context)
        mProgressDialog?.show()
    }

    override fun loadingDone() {
        mProgressDialog?.dismiss()
    }

    override fun insertFavoriteSuccesss() {
        lifecycleScope.launch(Dispatchers.Main){
            this@HomeFragment.context?.apply {
                showToast(resources.getString(R.string.addFavoriteSuccesss))
                mPresenter?.let { it.readQuotesDB() }
            }
        }
    }

    override fun insertFavoriteFail(exception: Exception) {
        lifecycleScope.launch(Dispatchers.Main){
            this@HomeFragment.context?.apply {
                showToast("${resources.getString(R.string.addFavoriteFail)}"+exception)
            }
        }
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

    override fun clickItemViewPlay(list: List<Quotes>, position: Int) {
        lifecycleScope.launch(Dispatchers.Main){
            val intent = Intent(this@HomeFragment.context , ViewPlayActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList(Constant.INTENT_VIEWPLAY_QUOTES , list as ArrayList)
            bundle.putInt(Constant.INTENT_VIEWPLAY_POSITION , position)
            intent.putExtras(bundle)
            startActivity(intent)
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
        this@HomeFragment.context?.switchActivity(AuthorActivity())
    }

    override fun clickItemTag(srtTag: String) {
        this@HomeFragment.context?.switchActivity(TagActivity())
    }

    override fun clickItemMore() {
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

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
