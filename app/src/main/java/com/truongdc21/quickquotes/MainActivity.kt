package com.truongdc21.quickquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.truongdc21.quickquotes.UI.Activity.ViewPlayActivity
import com.truongdc21.quickquotes.UI.Fragment.FavoriteFragment
import com.truongdc21.quickquotes.UI.Fragment.HomeFragment
import com.truongdc21.quickquotes.UI.Fragment.SearchFragment
import com.truongdc21.quickquotes.Utils.replaceFragmentBottomNAV
import com.truongdc21.quickquotes.Utils.switchActivity
import com.truongdc21.quickquotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var itemIDBottomNav : Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNav()
    }

    private fun setBottomNav() {
        binding.apply {
            this@MainActivity.replaceFragmentBottomNAV(HomeFragment())
            itemIDBottomNav = R.id.acction_home
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.acction_home -> {
                        this@MainActivity.replaceFragmentBottomNAV(HomeFragment())
                        itemIDBottomNav = item.itemId
                    }
                    R.id.acction_viewplay -> this@MainActivity.switchActivity(ViewPlayActivity())

                    R.id.acction_search -> {
                        this@MainActivity.replaceFragmentBottomNAV(SearchFragment())
                        itemIDBottomNav = item.itemId
                    }
                    R.id.acction_favorite -> {
                        this@MainActivity.replaceFragmentBottomNAV(FavoriteFragment())
                        itemIDBottomNav = item.itemId
                    }
                }
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavigationView.selectedItemId = itemIDBottomNav
    }
}
