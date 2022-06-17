package com.truongdc21.quickquotes.ui.adapter

<<<<<<< HEAD
import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
=======
import android.graphics.Typeface
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.truongdc21.quickquotes.R
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.databinding.LayoutItemFavoriteBinding
import com.truongdc21.quickquotes.ui.adapter.contract.FavoriteAdapterContract
import com.truongdc21.quickquotes.utils.Constant

class FavoriteAdapter(
    private val clickRemove: FavoriteAdapterContract.iClickRemove,
    private val clickItem: FavoriteAdapterContract.iClickItem
    ): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var mKey: String? = null
    private var mlistQuotes = mutableListOf<Quotes>()
    private var mlistAuthor = mutableListOf<Author>()
    private var mlistTag = mutableListOf<Tag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = LayoutItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        when(mKey){
            Constant.QUOTES -> {
                holder.bindQuotes(mlistQuotes.get(position) , position)
            }

            Constant.AUTHOR -> {
                holder.bindAuthor(mlistAuthor.get(position) , position)
            }

            Constant.TAG -> {
                holder.bindTag(mlistTag.get(position) , position)
            }
        }
    }

    override fun getItemCount(): Int {
        when(mKey){
            Constant.QUOTES -> return mlistQuotes.size
            Constant.AUTHOR -> return mlistAuthor.size
            else -> return mlistTag.size
        }
    }

    private fun View.setClickRemove(id : Int){
        when (mKey){
            Constant.QUOTES -> {
                this.setOnClickListener {
                    clickRemove.clickRemoveQuotes(id)
                }
            }

            Constant.AUTHOR -> {
                this.setOnClickListener {
                    clickRemove.clickRemoveAuthor(id)
                }
            }

            Constant.TAG -> {
                this.setOnClickListener {
                    clickRemove.clickRemveTag(id)
                }
            }
        }
    }

<<<<<<< HEAD
    private fun View.setClickItem(quotes: Quotes? , author: Author? , tag: Tag? , position: Int?){
        when (mKey){
            Constant.QUOTES -> {
                this.setOnClickListener {
                    quotes?.let {
                        position?.let {
                            clickItem.clickItemQuotes(mlistQuotes, it )
                        }
                    }
=======
    private fun View.setClickItem(quotes: Quotes? , author: Author? , tag: Tag?){
        when (mKey){
            Constant.QUOTES -> {
                this.setOnClickListener {
                  quotes?.let {
                      clickItem.clickItemQuotes(it)
                  }
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
                }
            }

            Constant.AUTHOR -> {
                this.setOnClickListener {
                   author?.let {
                       clickItem.clickItemAuthor(it)
                   }
                }
            }

            Constant.TAG -> {
                this.setOnClickListener {
                   tag?.let {
                       clickItem.clickItemTag(it)
                   }
                }
            }
        }
    }

<<<<<<< HEAD

    fun setDataQuotes(mList : List<Quotes>, key : String){
=======
    fun setDataQuotes(mList : List<Quotes> , key : String){
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
        this.mlistQuotes.apply {
            clear()
            addAll(mList)
        }
        this.mKey = key
        notifyDataSetChanged()
    }

    fun setDataAuthor(mList : List<Author>, key : String){
        this.mlistAuthor.apply {
            clear()
            addAll(mList)
        }
        this.mKey = key
        notifyDataSetChanged()
    }

<<<<<<< HEAD
    fun setDataTag(mList : List<Tag>, key: String){
=======
    fun setDataTag(mList : List<Tag> , key: String){
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
        this.mlistTag.apply {
            clear()
            addAll(mList)
        }
        this.mKey = key
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(
        private val binding: LayoutItemFavoriteBinding
        ): RecyclerView.ViewHolder(binding.root) {

        fun bindQuotes(itemQuotes: Quotes, position: Int) {
<<<<<<< HEAD

=======
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
            binding.apply {
                itemTVONE.text = itemQuotes.mQuotes
                itemTVTWO.text = itemQuotes.Author
                itemTVTWO.visibility= View.VISIBLE
                itemTVONE.setTypeface(itemTVONE.typeface, Typeface.ITALIC)
                itemImg.setBackgroundResource(R.drawable.ic_quotes)
                itemImgRemove.setClickRemove(itemQuotes.id)
<<<<<<< HEAD
                itemTVONE.setClickItem(itemQuotes, null  , null , position)
                itemTVTWO.setClickItem(itemQuotes, null  , null , null)
                cardView.setClickItem(itemQuotes, null  , null, null)
=======
                itemTVONE.setClickItem(itemQuotes, null  , null)
                itemTVTWO.setClickItem(itemQuotes, null  , null)
                cardView.setClickItem(itemQuotes, null  , null)
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
            }
        }

        fun bindAuthor(itemAuthor: Author, position: Int) {
            binding.apply {
                itemTVTWO.visibility = View.GONE
                itemTVONE.text = itemAuthor.mAuthor
                itemImg.setBackgroundResource(R.drawable.ic_author)
                itemImgRemove.setClickRemove(itemAuthor.id)
<<<<<<< HEAD
                itemTVONE.setClickItem(null , itemAuthor , null, null)
                cardView.setClickItem(null , itemAuthor ,null, null)
=======
                itemTVONE.setClickItem(null , itemAuthor , null)
                cardView.setClickItem(null , itemAuthor ,null)
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
            }
        }

        fun bindTag(itemTag: Tag, position: Int) {
            binding.apply {
                itemTVTWO.visibility = View.GONE
                itemTVONE.text = itemTag.mTag
                itemTVONE.setTypeface(itemTVONE.typeface, Typeface.BOLD_ITALIC)
                itemImg.setBackgroundResource(R.drawable.ic_lable)
                itemImgRemove.setClickRemove(itemTag.id)
<<<<<<< HEAD
                itemTVONE.setClickItem(null , null , itemTag , null)
                cardView.setClickItem(null , null ,itemTag , null)
=======
                itemTVONE.setClickItem(null , null , itemTag)
                cardView.setClickItem(null , null ,itemTag)
>>>>>>> f8640f9... Create UI favorite, Handle event, Fetch data
            }
        }
    }
}