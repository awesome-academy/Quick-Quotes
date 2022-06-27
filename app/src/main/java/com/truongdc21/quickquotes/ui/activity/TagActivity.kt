package com.truongdc21.quickquotes.ui.activity

import android.app.ProgressDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.databinding.ActivityTagBinding
import com.truongdc21.quickquotes.presenter.activityTag.TagActivityContract
import com.truongdc21.quickquotes.presenter.activityTag.TagActivityPresenter
import com.truongdc21.quickquotes.ui.adapter.QuotesAdapter
import com.truongdc21.quickquotes.ui.adapter.contract.QuotesAdapterConstract
import com.truongdc21.quickquotes.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagActivity:
    BaseActivity<ActivityTagBinding>(ActivityTagBinding::inflate),
    TagActivityContract.View,
    QuotesAdapterConstract.ClickItem {

    private var mPresenter : TagActivityPresenter? = null
    private val adapterQuotes by lazy { QuotesAdapter(this) }
    private var mProgressDialog : ProgressDialog? = null

    override fun initViews() {
        binding.apply {
            rvScreenTag.layoutManager = LinearLayoutManager(this@TagActivity)
            rvScreenTag.adapter = adapterQuotes
        }
    }

    override fun initData() {
        mPresenter = TagActivityPresenter(
            this,
            InitRepository.initRepositoryQuotes(this),
            InitRepository.initRepositoryAuthor(this),
            InitRepository.initRepositoryTag(this)
        )
        mPresenter?.setView(this)
        getStringIntent()
    }

    override fun showAdapterTag(mList: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterQuotes.setListQuotes(mList , this@TagActivity)
            binding.tvNumber.text = "#${mList.size} ${resources.getString(R.string.quotes)}"
        }
    }

    override fun showAdapterListLocal(mList: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.Main){
            binding.apply {
                adapterQuotes.setListQuotesLocal(mList)
            }
        }
    }

    override fun onSuccess(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@TagActivity.showToast(message)
        }
    }

    override fun onError(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@TagActivity.showToast(message)
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

    override fun loadingPropressbar() {
        mProgressDialog = ProgressDialog(this@TagActivity)
        mProgressDialog?.let {
            it.setCancelable(false)
            it.show()
            it.setContentView(R.layout.layout_custom_propressdialog)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)

        }
    }

    override fun loadingSuccess() {
        mProgressDialog?.dismiss()
    }

    override fun clickItemViewPlay(list: List<Quotes>, position: Int) {
        this.switchViewPlayActivity(list , position)
    }

    override fun clickItemCopy(srtQuotes: String) {
        this.copyToClipboard(srtQuotes)
    }

    override fun clickItemFavorite(quotes: Quotes) {
        lifecycleScope.launch(Dispatchers.IO){
            mPresenter?.insertQuotesDB(quotes)
            mPresenter?.readQuotesDB()
        }
    }

    override fun clickItemAuthor(srtAuthor: String) {
        this.switchAuthorActivity(srtAuthor)
    }

    override fun clickItemTag(srtTag: String) {
        return
    }

    override fun clickItemMore() {
        mPresenter?.getListAuthor()
        mPresenter?.getListAuthor()
    }

    override fun clickRemoveFavorite(quotes: Quotes, mListQuotesLocal: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.IO){
            mPresenter?.deleteQuotesDB(quotes , mListQuotesLocal)
        }
    }

    override fun clickItemFavoriteAuthor(srtAuthor: String) {
        mPresenter?.checkFavoriteAuthor(srtAuthor)
    }

    override fun clickItemFavoriteTag(srtTag: String) {
        mPresenter?.checkFavoriteTag(srtTag)
    }

    private fun getStringIntent(){
        val intent = getIntent()
        val key = intent.getStringExtra(Constant.INTEN_TAG)
        key?.let {
            mPresenter?.getListTagFromAPI(it)
            binding.tvTag.text = "@ ${key}"
        }
    }
}
