package com.truongdc21.quickquotes.ui.activity

import android.app.ProgressDialog
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.databinding.ActivityViewPlayBinding
import com.truongdc21.quickquotes.databinding.LayoutBottomSheetDialogViewplayBinding
import com.truongdc21.quickquotes.presenter.activityViewPlay.ViewPlayActivityContract
import com.truongdc21.quickquotes.presenter.activityViewPlay.ViewPlayActivityPresenter
import com.truongdc21.quickquotes.ui.adapter.ViewPlayAdapter
import com.truongdc21.quickquotes.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewPlayActivity:
    BaseActivity<ActivityViewPlayBinding>(ActivityViewPlayBinding::inflate),
    ViewPlayActivityContract.View {

    private var mPresenter: ViewPlayActivityPresenter? = null
    private val adapterViewPlay by lazy { ViewPlayAdapter(this) }
    private var mProgressDialog : ProgressDialog? = null

    override fun initViews() {
        binding.apply {
            vpgViewPlay.adapter = adapterViewPlay
            vpgViewPlay.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mPresenter?.getPositionItemQuotes(position)

                }
            })
        }
    }

    override fun initData() {
        mPresenter = ViewPlayActivityPresenter(
            this ,
            InitRepository.initRepositoryQuotes(this),
            InitRepository.initRepositoryAuthor(this),
            InitRepository.initRepositoryTag(this)
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

    override fun showItemFromApiReload(position: Int) {
        binding.vpgViewPlay.currentItem = position
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

    override fun translateQuotes(boolean: Boolean, srtQuotes: String) {
        if (boolean){
            binding.tvQuotes.translateToVietnam(srtQuotes)
            binding.imgTranslate.setBackgroundResource(R.drawable.ic_translate2)
        }else {
            binding.tvQuotes.translateToEnglish(srtQuotes)
            binding.imgTranslate.setBackgroundResource(R.drawable.ic_translate)
        }
    }

    override fun showAdapterLaterRemove(mList: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterViewPlay.setData(mList)
        }
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

    override fun loadingSuccess() {
        mProgressDialog?.dismiss()
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
                mPresenter?.checkItemFavorite(it , mPositionJump)
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

                showBottomSheetDialog(mList, position)
                mPresenter?.getDataAuthor()
                mPresenter?.getDataTag()
            }

            imgShared.setOnClickListener {
                it.setAnimationClick()
                this@ViewPlayActivity.sharedText(
                    resources.getString(R.string.share_quotes),
                    mList[position].mQuotes
                )
            }

            imgCopy.setOnClickListener {
                it.setAnimationClick()
                this@ViewPlayActivity.copyToClipboard(mList[position].mQuotes)
            }

            imgTranslate.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.translateQuotes(position)
            }

            imgFavorite.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.clickItemFavorite(mList , position)
            }
        }
    }

    private fun showBottomSheetDialog (mList: List<Quotes>, position: Int){
        val viewDialog = LayoutBottomSheetDialogViewplayBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog.apply {
            setContentView(viewDialog.root)
            show()
        }
        val isCheckAuthor = mPresenter?.checkItemFavoriteAuthotBottomSheet(mList[position].Author)
        val isCheckTag = mPresenter?.checkItemFavoriteTagBottomSheet(mList[position].Tag)

        lifecycleScope.launch(Dispatchers.Main){
            isCheckAuthor?.let {
                if(it){
                    viewDialog.imgItemViewPlayFavoriteAuthor.setBackgroundResource(R.drawable.ic_favorite)
                }else{
                    viewDialog.imgItemViewPlayFavoriteAuthor.setBackgroundResource(R.drawable.ic_favorite2)
                }
            }
        }

        lifecycleScope.launch(Dispatchers.Main){
            isCheckTag?.let {
                if(it){
                    viewDialog.imgItemViewPlayFavoriteTag.setBackgroundResource(R.drawable.ic_favorite)
                }else{
                    viewDialog.imgItemViewPlayFavoriteTag.setBackgroundResource(R.drawable.ic_favorite2)
                }
            }
        }

        viewDialog.apply {
            imgHideBottomSheetViewPlay.setOnClickListener {
                it.setAnimationClick()
                bottomSheetDialog.dismiss()
            }
            viewRemoveViewPlay.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.removeItemQuotes(position)
                bottomSheetDialog.dismiss()
            }

            viewReloadViewPlay.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.getListQuotesApiReload()
                bottomSheetDialog.dismiss()
            }

            viewSaveAuthorViewPlay.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.checkFavoriteAuthor(mList[position].Author)
                bottomSheetDialog.dismiss()
            }

            viewSaveTagViewPlay.setOnClickListener {
                it.setAnimationClick()
                mPresenter?.checkFavoriteTag(mList[position].Tag)
                bottomSheetDialog.dismiss()
            }
        }
    }
}
