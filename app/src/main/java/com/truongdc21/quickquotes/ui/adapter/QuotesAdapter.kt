package com.truongdc21.quickquotes.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.databinding.LayoutItemQuotesBinding
import com.truongdc21.quickquotes.ui.adapter.contract.QuotesAdapterConstract
import com.truongdc21.quickquotes.utils.setAnimationClick
import com.truongdc21.quickquotes.utils.sharedText
import com.truongdc21.quickquotes.utils.translateToEnglish
import com.truongdc21.quickquotes.utils.translateToVietnam
import kotlinx.coroutines.*


class QuotesAdapter(
    private val mContext: Context
    ): RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    private var mListQuotes = mutableListOf<Quotes>()
    private var mListQuotesLocal = mutableListOf<Quotes>()
    private var mListAuthorLocal = mutableListOf<Author>()
    private var mListTagLocal = mutableListOf<Tag>()
    private var quoteAdapterContract : QuotesAdapterConstract.ClickItem? = null
    private var mListNumberCheckTranslateAPI = mutableListOf<Int>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding = LayoutItemQuotesBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return QuotesViewHolder(binding.root , binding)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(mListQuotes.get(position) , position)
    }

    override fun getItemCount() = mListQuotes.size

    private fun checkItemFavorite(imgItemFavorite: ImageView, itemQuotes: Quotes, position: Int) {
        for (i in mListQuotesLocal){
            if (itemQuotes.mQuotes == i.mQuotes){
                imgItemFavorite.setBackgroundResource(R.drawable.ic_favorite2)
                return
            }
        }
        imgItemFavorite.setBackgroundResource(R.drawable.ic_favorite)
    }

    private fun clickItemFavorite(itemQuotes: Quotes) {
        for (i in mListQuotesLocal) {
            when (itemQuotes.mQuotes) {
                i.mQuotes -> {
                    quoteAdapterContract?.clickRemoveFavorite(itemQuotes, mListQuotesLocal)
                    return
                }
            }
        }
        quoteAdapterContract?.clickItemFavorite(itemQuotes)
    }

    private fun checkTranslate(
        position: Int,
        tvTranslate : TextView
    ){
        var isCheck = true
        for (i in mListNumberCheckTranslateAPI){
            if (position == i){
                tvTranslate.text = mContext.resources.getString(R.string.translate_to_english)
                isCheck = false
                break
            }
        }
        if (isCheck){
            tvTranslate.text = mContext.resources.getString(R.string.translate_to_vietnam)
        }
    }

    private fun checkFavoriteTagBottomSheet(
        itemQuotes: Quotes,
        imgFavoriteTag: ImageView,
        tvFavoriteATag: TextView
    ){
        CoroutineScope(Dispatchers.Default).launch {
            var isCheck = true
            for (i in mListTagLocal){
                if (itemQuotes.Tag == i.mTag){
                    withContext(Dispatchers.Main){
                        imgFavoriteTag.setBackgroundResource(R.drawable.ic_favorite2)
                        tvFavoriteATag.text = mContext.resources.getString(R.string.remove_tag_from_favorite)
                    }
                    isCheck = false
                    break
                    this.cancel()
                }
            }
            if (isCheck){
                withContext(Dispatchers.Main){
                    imgFavoriteTag.setBackgroundResource(R.drawable.ic_favorite)
                    tvFavoriteATag.text = mContext.resources.getString(R.string.add_tag_favorite)
                }
            }
        }
    }

    private fun checkFavoriteAuthorBottomSheet(
        itemQuotes: Quotes,
        imgFavoriteAuthor: ImageView,
        tvFavoriteAuthor: TextView
    ){
        CoroutineScope(Dispatchers.Default).launch {
            launch {
                var isCheck = true
                for (i in mListAuthorLocal) {
                    if (itemQuotes.Author == i.mAuthor) {
                        withContext(Dispatchers.Main) {
                            imgFavoriteAuthor.setBackgroundResource(R.drawable.ic_favorite2)
                            tvFavoriteAuthor.text = mContext.resources.getString(R.string.remove_author_from_favorite)
                        }
                        isCheck = false
                        break
                        this.cancel()
                    }
                }
                if (isCheck) {
                    withContext(Dispatchers.Main) {
                        imgFavoriteAuthor.setBackgroundResource(R.drawable.ic_favorite)
                        tvFavoriteAuthor.text = mContext.resources.getString(R.string.add_author_favorite)
                    }
                }
            }
        }
    }

    private fun showButtonSheetDialog(
        binding: LayoutItemQuotesBinding,
        itemQuotes: Quotes,
        position: Int
    ){
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_bottom_sheet_dialog , null)
        val bottomSheetDialog = BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme)
        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
        val viewRemove = view.findViewById<LinearLayout>(R.id.viewRemove)
        val viewShared = view.findViewById<LinearLayout>(R.id.viewShared)
        val viewSaveAuthor = view.findViewById<LinearLayout>(R.id.viewSaveAuthor)
        val viewSaveTag = view.findViewById<LinearLayout>(R.id.viewSaveTag)
        val viewTranslate = view.findViewById<LinearLayout>(R.id.viewTranslate)
        val imgFavoriteAuthor = view.findViewById<ImageView>(R.id.imgItemFavoriteAuthor)
        val imgFavoriteTag = view.findViewById<ImageView>(R.id.imgItemFavoriteTag)
        val tvFavoriteAuthor = view.findViewById<TextView>(R.id.tvItemFavoriteAuthor)
        val tvFavoriteTag = view.findViewById<TextView>(R.id.tvItemFavoriteTag)
        val imgHideBottomSheet = view.findViewById<ImageView>(R.id.imgHideBottomSheet)
        val tvTranslate = view.findViewById<TextView>(R.id.tvTranslate)

        checkTranslate(position, tvTranslate)
        checkFavoriteAuthorBottomSheet(itemQuotes, imgFavoriteAuthor,tvFavoriteAuthor)
        checkFavoriteTagBottomSheet(itemQuotes, imgFavoriteTag, tvFavoriteTag)

        onClickBtSheetHide(position , itemQuotes , imgHideBottomSheet , bottomSheetDialog)
        onClickBtSheetRemove(position, viewRemove , bottomSheetDialog)
        onClickBtSheetShared( itemQuotes , viewShared , bottomSheetDialog)
        onClickBtSheetAuthor( itemQuotes , viewSaveAuthor , bottomSheetDialog)
        onClickBtSheetTag(itemQuotes , viewSaveTag , bottomSheetDialog)
        onClickBtSheeTranslate(position , itemQuotes , viewTranslate , bottomSheetDialog , binding)
    }

    private fun onClickBtSheetHide(position: Int, itemQuotes: Quotes, imgHideBottomSheet: ImageView?, bottomSheetDialog: BottomSheetDialog) {
        imgHideBottomSheet?.setOnClickListener {
            it.setAnimationClick()
            bottomSheetDialog.dismiss()
        }
    }

    private fun onClickBtSheetRemove(
        position: Int,
        viewRemove: LinearLayout?,
        bottomSheetDialog: BottomSheetDialog
    ){
        viewRemove?.setOnClickListener {
            it.setAnimationClick()
            mListQuotes.removeAt(position)
            notifyItemRemoved(position)
            bottomSheetDialog.dismiss()
        }
    }

    private fun onClickBtSheetShared(
        itemQuotes: Quotes,
        viewShared: LinearLayout?,
        bottomSheetDialog: BottomSheetDialog
    ){
        viewShared?.setOnClickListener {
            it.setAnimationClick()
            mContext.apply {
                sharedText(resources.getString(R.string.share_quotes) , itemQuotes.mQuotes)
            }
            bottomSheetDialog.dismiss()
        }
    }

    private fun onClickBtSheetAuthor(
        itemQuotes: Quotes,
        viewSaveAuthor: LinearLayout?,
        bottomSheetDialog: BottomSheetDialog
    ){
        viewSaveAuthor?.setOnClickListener {
            it.setAnimationClick()
            quoteAdapterContract?.clickItemFavoriteAuthor(itemQuotes.Author)
            bottomSheetDialog.dismiss()
        }
    }

    private fun onClickBtSheetTag(
        itemQuotes: Quotes,
        viewSaveTag: LinearLayout?,
        bottomSheetDialog: BottomSheetDialog
    ){
        viewSaveTag?.setOnClickListener {
            it.setAnimationClick()
            quoteAdapterContract?.clickItemFavoriteTag(itemQuotes.Tag)
            quoteAdapterContract?.clickItemMore()
            bottomSheetDialog.dismiss()
        }
    }

    private fun onClickBtSheeTranslate(
        position: Int,
        itemQuotes: Quotes,
        viewTranslate: LinearLayout?,
        bottomSheetDialog: BottomSheetDialog,
        binding : LayoutItemQuotesBinding
    ){
        viewTranslate?.setOnClickListener {
            it.setAnimationClick()
            var isCheck = true
            for (i in mListNumberCheckTranslateAPI){
                if (position == i){
                    binding.tvItemQuotes.translateToEnglish(itemQuotes.mQuotes)
                    mListNumberCheckTranslateAPI.removeAt(mListNumberCheckTranslateAPI.indexOf(i))
                    isCheck = false
                    break
                }
            }
            if (isCheck){
                binding.tvItemQuotes.translateToVietnam(itemQuotes.mQuotes)
                mListNumberCheckTranslateAPI.add(position)
            }
            bottomSheetDialog.dismiss()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListQuotes (
        mList : List<Quotes>,
        quotesAdapterConstract: QuotesAdapterConstract.ClickItem
    ){
        this.quoteAdapterContract = quotesAdapterConstract
        mListQuotes.apply {
            clear()
            addAll(mList)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListQuotesLocal (mListLocal: List<Quotes>){
        mListQuotesLocal.apply {
            clear()
            addAll(mListLocal)
        }
        notifyDataSetChanged()
    }

    fun setListAuthorLocal(mList: List<Author>) {
        mListAuthorLocal.apply {
            clear()
            addAll(mList)
        }
    }

    fun setListTagLocal(mList: List<Tag>) {
        mListTagLocal.apply {
            clear()
            addAll(mList)
        }
    }

    inner class QuotesViewHolder(
        itemView :View ,
        private val binding : LayoutItemQuotesBinding
        ): RecyclerView.ViewHolder(itemView) {

        fun bind (itemQuotes: Quotes , position: Int) {
            binding.apply {
                checkItemFavorite(imgItemFavorite , itemQuotes , position)
                itemNameAuthor.text = itemQuotes.Author
                tvItemQuotes.text = itemQuotes.mQuotes
                itemTvTag.text = itemQuotes.Tag

                CoroutineScope(Dispatchers.Main).launch{
                    mContext.let {
                        Glide.with(it).load(itemQuotes.urlImage).into(itemIMGHomeQuotes)
                    }
                }

                bindCopy(imgItemCoppy ,itemQuotes)
                bindViewPlay(imgItemViewPlay, position)
                bindFavorite(imgItemFavorite , itemQuotes)
                bindMoreClick(itemImgMore, binding, position, itemQuotes)
                bindImgAuthorClick(imgItemAuthor, itemQuotes)
                bindNameAuthor(itemNameAuthor, itemQuotes)
                bindTVTag(itemTvTag, itemQuotes)

            }
        }

        private fun bindTVTag(itemTvTag: TextView, itemQuotes: Quotes) {
            itemTvTag.setOnClickListener {
                it.setAnimationClick()
                quoteAdapterContract?.clickItemTag(itemQuotes.Tag)
            }
        }

        private fun bindNameAuthor(itemNameAuthor: TextView, itemQuotes: Quotes) {
            itemNameAuthor.setOnClickListener {
                it.setAnimationClick()
                quoteAdapterContract?.clickItemAuthor(itemQuotes.Author)
            }
        }

        private fun bindImgAuthorClick(imgItemAuthor: ImageView, itemQuotes: Quotes) {
            imgItemAuthor.setOnClickListener {
                it.setAnimationClick()
                quoteAdapterContract?.clickItemAuthor(itemQuotes.Author)
            }
        }

        private fun bindMoreClick(
            itemImgMore: ImageView,
            binding: LayoutItemQuotesBinding,
            position: Int,
            itemQuotes: Quotes
        ){
            itemImgMore.setOnClickListener {
                it.setAnimationClick()
                quoteAdapterContract?.clickItemMore()
                showButtonSheetDialog(
                    binding,
                    itemQuotes,
                    position
                )
            }
        }

        private fun bindFavorite(imgItemFavorite: ImageView , itemQuotes: Quotes) {
            imgItemFavorite.setOnClickListener {
                it.setAnimationClick()
                clickItemFavorite(itemQuotes)
            }
        }

        private fun bindViewPlay(imgItemViewPlay: ImageView, position: Int) {
            imgItemViewPlay.setOnClickListener {
                it.setAnimationClick()
                quoteAdapterContract?.clickItemViewPlay(mListQuotes , position)
            }
        }

        private fun bindCopy(imgItemCoppy: ImageView, itemQuotes: Quotes) {
            imgItemCoppy.setOnClickListener {
                it.setAnimationClick()
                quoteAdapterContract?.clickItemCopy(itemQuotes.mQuotes)
            }
        }
    }
}
