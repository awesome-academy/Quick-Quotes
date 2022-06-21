package com.truongdc21.quickquotes.ui.activity

import android.app.ProgressDialog
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.truongdc21.quickquotes.presenter.SearchViewPagerFragment.SearchViewPagerFragmentContract
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.databinding.ActivitySearchBinding
import com.truongdc21.quickquotes.presenter.activitySearch.SearchActivityContract
import com.truongdc21.quickquotes.presenter.activitySearch.SearchActivityPresenter
import com.truongdc21.quickquotes.ui.adapter.SearchAdapterViewpager
import com.truongdc21.quickquotes.ui.fragment.SearchViewPagerFragment
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.InitRepository

class SearchActivity:
    BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    SearchActivityContract.View,
    androidx.appcompat.widget.SearchView.OnQueryTextListener{

    private var mPresenter: SearchActivityPresenter? = null
    private var mProgressDialog : ProgressDialog? = null
    private val adapterSearchVPG = SearchAdapterViewpager(supportFragmentManager , lifecycle)
    private var iViewVPG : SearchViewPagerFragmentContract.ViewPageFragment? = null

    override fun initViews() {

        binding.apply {

            querySearchView()

            vpgSearch.adapter = adapterSearchVPG
            TabLayoutMediator(tabLayoutSearch, vpgSearch , object : TabLayoutMediator.TabConfigurationStrategy{
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    when (position) {
                        Constant.TAB_QUOTES -> {
                            tab.text = resources?.getString(R.string.quotes)
                        }
                        Constant.TAB_AUTHOR -> {
                            tab.text = resources?.getString(R.string.author)
                        }
                        Constant.TAB_TAG -> {
                            tab.text = resources?.getString(R.string.tag)
                        }
                    }
                }
            }).attach()

            vpgSearch.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mPresenter?.setPositionItemQuotes(position)
                }
            })
        }
    }

    override fun initData() {
        mPresenter = SearchActivityPresenter(
            this,
            InitRepository.initRepositorySearch(this)
        )
        mPresenter?.setView(this)
        iViewVPG = SearchViewPagerFragment()
    }

    override fun sendSearchQuotestoFragment(mList: List<Search>) {
        iViewVPG?.showAdapterQuotesSearch(mList)
    }

    override fun sendSearchAuthorFragment(mList: List<Search>) {
        iViewVPG?.showAdapterAuhtorSearch(mList)
    }

    override fun sendSearchTagFragment(mList: List<Search>) {
        iViewVPG?.showAdapterTagSearch(mList)
    }

    override fun loadingPropressbar() {
        mProgressDialog = ProgressDialog(this)
        mProgressDialog?.let {
            it.setCancelable(false)
            it.show()
            it.setContentView(R.layout.layout_custom_propressdialog)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
    }

    override fun loadingSuccsess() {
        mProgressDialog?.dismiss()
    }

    override fun onQueryTextSubmit(text : String?): Boolean {
        text?.let {
            iViewVPG?.queryTextSubmitSearch(it)
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        text?.let {
            iViewVPG?.queryTextChangeSearch(it)
        }
       return true
    }

    private fun querySearchView(){
        val querySeatch = intent.getStringExtra(Constant.PUT_STRING_SEARCH)
        querySeatch.let {
            binding.searchViewActivity.setQuery(it , true)
            onQueryTextSubmit(it)
        }
        binding.searchViewActivity.setOnQueryTextListener(this@SearchActivity)

    }
}
