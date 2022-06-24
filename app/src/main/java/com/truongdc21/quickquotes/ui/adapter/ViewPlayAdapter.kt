package com.truongdc21.quickquotes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.databinding.LayoutItemViewplayBinding
import com.truongdc21.quickquotes.utils.showImageGlideWithURL

class ViewPlayAdapter(
    private val mContext: Context
    ): RecyclerView.Adapter<ViewPlayAdapter.ViewPlayViewHolder>() {

    private var mListQuotes = mutableListOf<Quotes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPlayViewHolder {
        val binding = LayoutItemViewplayBinding.inflate(LayoutInflater.from(parent.context) , parent ,false)
        return ViewPlayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPlayViewHolder, position: Int) {
        holder.bind(mListQuotes[position])
    }

    override fun getItemCount() = mListQuotes.size

    fun setData (mList : List<Quotes>){
        this.mListQuotes.apply {
            clear()
            addAll(mList)
        }
        notifyDataSetChanged()
    }

    inner class ViewPlayViewHolder(
        private val binding : LayoutItemViewplayBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(itemQuotes: Quotes) {
            binding.apply {
                imgItemQuotes.showImageGlideWithURL(mContext, itemQuotes.urlImage)
            }
        }
    }
}
