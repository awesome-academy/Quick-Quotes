package com.truongdc21.quickquotes.ui.activity

import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.databinding.ActivityViewPlayBinding
import com.truongdc21.quickquotes.presenter.activityViewPlay.ViewPlayActivityContract
import com.truongdc21.quickquotes.presenter.activityViewPlay.ViewPlayActivityPresenter
import com.truongdc21.quickquotes.ui.adapter.ViewPlayAdapter
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.InitRepository
import com.truongdc21.quickquotes.utils.setAnimationClick
import com.truongdc21.quickquotes.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewPlayActivity:
    BaseActivity<ActivityViewPlayBinding>(ActivityViewPlayBinding::inflate),
    ViewPlayActivityContract.View {

    private var mPresenter: ViewPlayActivityPresenter? = null
    private val adapterViewPlay by lazy { ViewPlayAdapter(this) }


    override fun initViews() {
        binding.apply {
            vpgViewPlay.adapter = adapterViewPlay
            vpgViewPlay.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mPresenter?.setPositionItemQuotes(position)
                }
            })
        }
    }

    override fun initData() {
        mPresenter = ViewPlayActivityPresenter(
            this ,
            InitRepository.initRepositoryQuotes(this)
        )
        mPresenter?.onStart()
        mPresenter?.setView(this)
        checkBundle()
    }

    override fun showAdapterAPIRandom(mList: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterViewPlay.setData(mList)
        }
    }

    override fun showItemQuotes(mList: List<Quotes>, position: Int) {
        lifecycleScope.launch(Dispatchers.Main){
            val srtAuthor = "${resources.getString(R.string.cross)} ${mList[position].Author}"
            binding.apply {
                tvQuotes.text = mList[position].mQuotes
                tvAuthor.text = srtAuthor
                tvTag.text = mList[position].Tag
            }
            setOnClick(mList, position)
            mPresenter?.checkItemFavorite(mList , position)
        }
    }

    override fun showItemFromItent(mList: List<Quotes>, position: Int) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterViewPlay.setData(mList)
            binding.vpgViewPlay.currentItem = position
        }
    }

    override fun checkFavorite(boolean: Boolean) {
        lifecycleScope.launch(Dispatchers.Main){
            if (boolean){
                binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite2)
            }else{
                binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite)
            }
        }
    }

    override fun onSuccess(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@ViewPlayActivity.showToast(message)
        }
    }

    override fun onError(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@ViewPlayActivity.showToast(message)
        }
    }

    private fun checkBundle() {
        val bundle = intent.extras
        if (bundle != null){
            val mList = bundle.getParcelableArrayList<Quotes>(Constant.INTENT_VIEWPLAY_QUOTES)?.toMutableList()
            val mPositionJump = bundle.getInt(Constant.INTENT_VIEWPLAY_POSITION)
            mList?.let {
                mPresenter?.setDataListFromItent(it , mPositionJump)
            }
        }else {
            mPresenter?.getListQuotesAPIRandom()
        }
    }

    private fun setOnClick(mList: List<Quotes>, position: Int) {
        binding.apply {

            imgBack.setOnClickListener {
                it.setAnimationClick()
                finish()
            }

            imgMore.setOnClickListener {
                it.setAnimationClick()
            }

            imgShared.setOnClickListener {
                it.setAnimationClick()
            }

            imgCopy.setOnClickListener {
                it.setAnimationClick()
            }

            imgCopy.setOnClickListener {
                it.setAnimationClick()
            }

            imgTranslate.setOnClickListener {
                it.setAnimationClick()

            }

            imgFavorite.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.clickItemFavorite(mList , position)
            }
        }
    }
}
