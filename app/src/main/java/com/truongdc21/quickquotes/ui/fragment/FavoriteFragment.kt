package com.truongdc21.quickquotes.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongdc21.quickquotes.data.model.Author
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.data.model.Tag
import com.truongdc21.quickquotes.databinding.FragmentFavoriteBinding
import com.truongdc21.quickquotes.presenter.fragmentFavorite.FavoriteFragmentContact
import com.truongdc21.quickquotes.presenter.fragmentFavorite.FavoriteFragmentPresenter
import com.truongdc21.quickquotes.ui.activity.AuthorActivity
import com.truongdc21.quickquotes.ui.activity.TagActivity
import com.truongdc21.quickquotes.ui.activity.ViewPlayActivity
import com.truongdc21.quickquotes.ui.adapter.FavoriteAdapter
import com.truongdc21.quickquotes.ui.adapter.contract.FavoriteAdapterContract
import com.truongdc21.quickquotes.utils.Constant
import com.truongdc21.quickquotes.utils.InitRepository
import com.truongdc21.quickquotes.utils.showToast
import com.truongdc21.quickquotes.utils.switchActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment(private val mKey : String):
    BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    FavoriteFragmentContact.View,
    FavoriteAdapterContract.iClickItem,
    FavoriteAdapterContract.iClickRemove {

    private var mPresenter: FavoriteFragmentPresenter? = null
    private val adapterFavorite by lazy { FavoriteAdapter(this , this) }

    override fun initViews() {
        lifecycleScope.launch(Dispatchers.Main){
            binding.apply {
                rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteFragment.context)
                rvFavorite.adapter = adapterFavorite
            }
            checkIsKeysetAdapter()
        }
    }

    override fun initData() {
        initPresenter()
    }

    override fun showAdapterQuotes(mlIst: List<Quotes>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterFavorite.setDataQuotes(mlIst , Constant.QUOTES)
        }
    }

    override fun showAdapterAuthor(mList: List<Author>) {
        lifecycleScope.launch(Dispatchers.Main){
            adapterFavorite.setDataAuthor(mList, Constant.AUTHOR)
        }
    }

    override fun showAdapterTag(mList: List<Tag>) {
       lifecycleScope.launch(Dispatchers.Main){
           adapterFavorite.setDataTag(mList, Constant.TAG)
       }
    }

    override fun removeSuccess(message: String) {
        lifecycleScope.launch(Dispatchers.Main){
            this@FavoriteFragment.context?.showToast(message)
        }
    }

    override fun clickRemoveQuotes(id: Int) {
        mPresenter?.removeQuotes(id)
    }

    override fun clickRemoveAuthor(id: Int) {
        mPresenter?.removeAuthor(id)
    }

    override fun clickRemveTag(id: Int) {
       mPresenter?.removeTag(id)
    }

    override fun clickItemQuotes(mList: List<Quotes> , position : Int) {
        lifecycleScope.launch(Dispatchers.Main){
            val intent = Intent(this@FavoriteFragment.context , ViewPlayActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList(Constant.INTENT_VIEWPLAY_QUOTES , mList as ArrayList)
            bundle.putInt(Constant.INTENT_VIEWPLAY_POSITION , position )
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun clickItemAuthor(author: Author) {
        lifecycleScope.launch(Dispatchers.Main){
            this@FavoriteFragment.context?.switchActivity(AuthorActivity())
        }
    }

    override fun clickItemTag(tag: Tag) {
        lifecycleScope.launch(Dispatchers.Main){
            this@FavoriteFragment.context?.switchActivity(TagActivity())
        }
    }

    override fun onError(message: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            this@FavoriteFragment.context?.showToast(message)
        }
    }

    private fun checkIsKeysetAdapter(){
        when(mKey){
            Constant.QUOTES -> mPresenter?.getListQuotesFavorite()
            Constant.AUTHOR -> mPresenter?.getListAuthorFavorite()
            else -> mPresenter?.getListTagFavorite()
        }
    }

    private fun initPresenter() {
        mPresenter = FavoriteFragmentPresenter(
            this@FavoriteFragment.requireContext(),
            InitRepository.initRepositoryQuotes(this.requireContext()),
            InitRepository.initRepositoryAuthor(this.requireContext()),
            InitRepository.initRepositoryTag(this.requireContext())
        )
        mPresenter?.setView(this)
    }
}
