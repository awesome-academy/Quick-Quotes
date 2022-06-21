package com.truongdc21.quickquotes.ui.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Search
import com.truongdc21.quickquotes.database.ConstanceDb
import com.truongdc21.quickquotes.databinding.LayoutItemSearchBinding
import com.truongdc21.quickquotes.ui.adapter.contract.SearchAdapterContract
import com.truongdc21.quickquotes.utils.Constant

class SearchAdapter(
    private val searchClickItem : SearchAdapterContract.ClickItem
    ):
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(),
    Filterable {

    private var mlistSearch = mutableListOf<Search>()
    private var mlistSearchHistory = mutableListOf<Search>()
    private var mlistSearchAPI = mutableListOf<Search>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = LayoutItemSearchBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(mlistSearch[position])
    }

    override fun getItemCount() = mlistSearch.size

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val srtSearch = p0.toString().toLowerCase()
                if (srtSearch.isEmpty()){
                    mlistSearch = mlistSearchHistory
                }else{
                    val listFilter = mutableListOf<Search>()
                    for (i in mlistSearchAPI){
                        i.text?.let {
                            it.toLowerCase()
                            if (it.contains(srtSearch)){
                                listFilter.add(i)
                            }
                        }
                    }
                    mlistSearch.apply {
                        clear()
                        addAll(listFilter)
                    }
                }
                var filterResult = FilterResults()
                filterResult.values = mlistSearch
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                mlistSearch = p1?.values as MutableList<Search>
                notifyDataSetChanged()
            }
        }
    }

    private fun SearchObject(search: Search, type : String): Search{
        return Search (search.id , search.key , search.text , type)
    }

    private fun View.setOnClickItem(itemSearch: Search) {
        this.setOnClickListener {
            when(itemSearch.key) {
                ConstanceDb.COLUMN_QUOTES -> {

                    searchClickItem.clickQuotes(SearchObject(itemSearch , Constant.LOCAL))
                }
                ConstanceDb.COLUMN_AUTHOR -> {
                    searchClickItem.clickAuthor(SearchObject(itemSearch , Constant.LOCAL))
                }
                ConstanceDb.COLUMN_TAG -> {
                    searchClickItem.clickTag(SearchObject(itemSearch , Constant.LOCAL))
                }
                ConstanceDb.COLUMN_HISTORY -> {
                    itemSearch.text?.let {
                        searchClickItem.clickHistory(it)
                    }
                }
            }
        }
    }

    private fun View.clickItemEnd(itemSearch: Search){
        this.setOnClickListener {
            searchClickItem.removeHistory(itemSearch)
        }
    }

    fun setDataHistory(mListHistory : List<Search>){
        this.mlistSearch.apply {
            clear()
            addAll(mListHistory)
        }
        notifyDataSetChanged()

    }

    fun setDataAPI(mListAPI : List<Search>){
        this.mlistSearchAPI.apply {
            clear()
            addAll(mListAPI)
        }
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(private val binding: LayoutItemSearchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemSearch: Search){
            binding.apply {

                itemTvSearchONE.text = itemSearch.text
                itemTvSearchONE.setOnClickItem(itemSearch)
                viewICon.setOnClickItem(itemSearch)
                itemImgEnd.clickItemEnd(itemSearch)

                if (itemSearch.type == Constant.LOCAL){
                    itemImgEnd.visibility = View.VISIBLE
                    itemImgEnd.setBackgroundResource(R.drawable.ic_close)
                }else {
                    itemImgEnd.visibility = View.INVISIBLE
                }

                when(itemSearch.key){
                    ConstanceDb.COLUMN_QUOTES -> {
                        itemImgSearch.setBackgroundResource(R.drawable.ic_quotes)
                        itemTvSearchONE.setTypeface(itemTvSearchONE.typeface, Typeface.ITALIC)
                    }
                    ConstanceDb.COLUMN_AUTHOR -> {
                        itemImgSearch.setBackgroundResource(R.drawable.ic_author)
                        itemTvSearchONE.setTypeface(itemTvSearchONE.typeface, Typeface.BOLD)
                    }
                    ConstanceDb.COLUMN_TAG -> {
                        itemImgSearch.setBackgroundResource(R.drawable.ic_lable)
                        itemTvSearchONE.setTypeface(itemTvSearchONE.typeface, Typeface.BOLD_ITALIC)
                    }
                    ConstanceDb.COLUMN_HISTORY -> {
                        itemImgSearch.setBackgroundResource(R.drawable.ic_tag)
                        itemTvSearchONE.setTypeface(itemTvSearchONE.typeface, Typeface.ITALIC)
                    }
                }
            }
        }
    }
}
