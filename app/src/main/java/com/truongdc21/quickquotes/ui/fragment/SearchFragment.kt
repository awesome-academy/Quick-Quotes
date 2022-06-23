package com.truongdc21.quickquotes.ui.fragment

import com.truongdc21.quickquotes.data.model.Quotes
import android.app.ProgressDialog
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.data.repository.SearchRepository
import com.truongdc21.quickquotes.data.source.local.SearchLocalSource
import com.truongdc21.quickquotes.data.source.remote.SearchRemoteSource
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.database.MyDatabaseHelper
import com.truongdc21.quickquotes.database.search.SearchDBIplm
import com.truongdc21.quickquotes.databinding.FragmentSearchBinding
import com.truongdc21.quickquotes.presenter.fragmentSearch.SearchFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentSearch.SearchFragmentPresenter
import com.truongdc21.quickquotes.ui.activity.AuthorActivity
import com.truongdc21.quickquotes.ui.activity.SearchActivity
import com.truongdc21.quickquotes.ui.activity.TagActivity
import com.truongdc21.quickquotes.ui.activity.ViewPlayActivity
import com.truongdc21.quickquotes.ui.adapter.SearchAdapter
import com.truongdc21.quickquotes.ui.adapter.contract.SearchAdapterContract
import com.truongdc21.quickquotes.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchFragment:
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),
    SearchView.OnQueryTextListener,
    SearchAdapterContract.ClickItem,
    SearchFragmentContact.View{

    private var mPresenter: SearchFragmentPresenter? = null
    private val adapterSearch by lazy { SearchAdapter(this) }
    private var mProgressDialog : ProgressDialog? = null

    override fun initViews() {
        binding.apply {
            searchView.setOnQueryTextListener(this@SearchFragment)
            rvSearch.layoutManager = LinearLayoutManager(this@SearchFragment.context)
            rvSearch.adapter = adapterSearch
        }
    }

    override fun initData() {
        mPresenter = SearchFragmentPresenter(
            this@SearchFragment.requireContext(),
            InitRepository.initRepositorySearch(this@SearchFragment.requireContext())
        )
        mPresenter?.setView(this)
        mPresenter?.onStart()
    }

    override fun onQueryTextSubmit(text: String?): Boolean {
        adapterSearch.filter.filter(text)
        if (!this@SearchFragment.context.isNetworkAvailable()){
            showToast(resources.getString(R.string.internet_disconnect))
            return true
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        if (text != null){
            binding.tvHistory.visibility = View.GONE
            binding.tvResultSearchNextActivitySearch.visibility = View.VISIBLE
        }
        if (text == ""){
            binding.tvResultSearchNextActivitySearch.visibility = View.GONE
            binding.tvHistory.visibility = View.VISIBLE
            mPresenter?.getListSearchHistory()
        }
        adapterSearch.filter.filter(text)
        return true
    }

    override fun showAdapterListAPI(mListSearch: List<Search>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterSearch.setDataAPI(mListSearch)
        }
    }

    override fun onError() {
    }

    override fun showToast(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@SearchFragment.context?.showToast(message)
        }
    }

    override fun showAdapterListHistory(mListSearch: List<Search>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterSearch.setDataHistory(mListSearch.reversed())
        }
    }

    override fun switchActivityViewPlay(mList: List<Quotes>) {
        this.context?.switchViewPlayActivity(mList , Constant.POSITION_ZERO)
    }

    override fun loadingApi() {
        mProgressDialog = ProgressDialog(this@SearchFragment.context)
        mProgressDialog?.let {
            it.setCancelable(false)
            it.show()
            it.setContentView(R.layout.layout_custom_propressdialog)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
    }

    override fun loadingApiSuccess() {
        mProgressDialog?.dismiss()
    }

    override fun removeHistorySuccess() {
        lifecycleScope.launch(Dispatchers.Main){
            this@SearchFragment.context?.showToast(resources.getString(R.string.remove_success))
        }
    }

    override fun clickQuotes(search: Search) {
        mPresenter?.insertSearchHistory(search)
        search.text?.let {
            mPresenter?.clickQuotesSearch(it)
        }
    }

    override fun clickAuthor(search: Search) {
        mPresenter?.insertSearchHistory(search)
        search.text?.let {
            this.context?.switchAuthorActivity(it)
        }
    }

    override fun clickTag(search: Search) {
        mPresenter?.insertSearchHistory(search)
        search.text?.let {
            this.context?.switchTagActivity(it)
        }
    }

    override fun clickHistory(text: String) {
    }

    override fun removeHistory(search: Search) {
        search.id?.let {
            mPresenter?.deleteSearchHistory(it)
        }
    }
}
