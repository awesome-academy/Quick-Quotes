package com.truongdc21.quickquotes.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView

import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.databinding.LayoutItemSearchBinding


class SearchAdapterVPG(

):
    RecyclerView.Adapter<SearchAdapterVPG.SearchViewHolder>(){

    private var mlist  = mutableListOf<Search>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = LayoutItemSearchBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mlist[position])
    }
    override fun getItemCount() = mlist.size

    fun setData(newlist : List<Search>) {
        mlist.addAll(newlist)
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: LayoutItemSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemSearch: Search){
            binding.apply {
                itemTvSearchONE.text = itemSearch.text
            }
        }
    }
}
