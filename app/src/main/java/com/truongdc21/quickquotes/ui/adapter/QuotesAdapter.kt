package com.truongdc21.quickquotes.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.databinding.LayoutItemQuotesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.ui.adapter.contract.QuotesAdapterConstract

class QuotesAdapter(
    private val context: Context
    ): RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    private var mListQuotes = mutableListOf<Quotes>()
    private var mListQuotesLocal = mutableListOf<Quotes>()
    var quoteAdapterContract : QuotesAdapterConstract.ClickItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding = LayoutItemQuotesBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return QuotesViewHolder(binding.root , binding)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(mListQuotes.get(position) , position)
    }

    override fun getItemCount() = mListQuotes.size

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

    inner class QuotesViewHolder(
        itemView :View ,
        private val binding : LayoutItemQuotesBinding) : RecyclerView.ViewHolder(itemView) {
        private val animationClick = AlphaAnimation(9f, 0.1f)
        fun bind (itemQuotes: Quotes , position: Int) {
            binding.apply {

                checkItemFavorite(imgItemFavorite , itemQuotes , position)
                itemNameAuthor.text = itemQuotes.Author
                tvItemQuotes.text = itemQuotes.mQuotes
                itemTvTag.text = itemQuotes.Tag
                CoroutineScope(Dispatchers.Main).launch{
                    context.let {
                        Glide.with(it).load(itemQuotes.urlImage).into(itemIMGHomeQuotes)
                    }
                }
                imgItemCoppy.setOnClickListener {
                    it.startAnimation(animationClick)
                    quoteAdapterContract?.clickItemCopy(itemQuotes.mQuotes)
                }
                imgItemViewPlay.setOnClickListener {
                    it.startAnimation(animationClick)
                    quoteAdapterContract?.clickItemViewPlay(mListQuotes , position)
                }
                imgItemFavorite.setOnClickListener {
                    clickItemFavorite(it , itemQuotes , position)
                }
                itemImgMore.setOnClickListener {
                    it.startAnimation(animationClick)
                    quoteAdapterContract?.clickItemMore()
                }
                itemNameAuthor.setOnClickListener {
                    it.startAnimation(animationClick)
                    quoteAdapterContract?.clickItemAuthor(itemQuotes.Author)
                }
                itemTvTag.setOnClickListener {
                    it.startAnimation(animationClick)
                    quoteAdapterContract?.clickItemTag(itemQuotes.Tag)
                }
            }
        }

        private fun checkItemFavorite(imgItemFavorite: ImageView, itemQuotes: Quotes, position: Int) {
            for (i in mListQuotesLocal){
                if (itemQuotes.mQuotes == i.mQuotes){
                    imgItemFavorite.setBackgroundResource(R.drawable.ic_favorite2)
                    return
                }
            }
            imgItemFavorite.setBackgroundResource(R.drawable.ic_favorite)
        }

        private fun clickItemFavorite(it: View? , itemQuotes: Quotes , position: Int) {
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
    }

}
