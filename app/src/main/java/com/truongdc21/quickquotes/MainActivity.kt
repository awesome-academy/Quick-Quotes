package com.truongdc21.quickquotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.truongdc21.quickquotes.UI.Activity.ViewPlayActivity
import com.truongdc21.quickquotes.UI.Fragment.FavoriteFragment
import com.truongdc21.quickquotes.UI.Fragment.HomeFragment
import com.truongdc21.quickquotes.UI.Fragment.SearchFragment
import com.truongdc21.quickquotes.Utils.ExtensionActivity.switchActivity
import com.truongdc21.quickquotes.Utils.ExtensionFragment.replaceFragment
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
            this@MainActivity.replaceFragment(HomeFragment())
            itemIDBottomNav = R.id.acction_home
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.acction_home -> {
                        this@MainActivity.replaceFragment(HomeFragment())
                        itemIDBottomNav = item.itemId
                    }
                    R.id.acction_viewplay -> this@MainActivity.switchActivity(ViewPlayActivity())

                    R.id.acction_search -> {
                        this@MainActivity.replaceFragment(SearchFragment())
                        itemIDBottomNav = item.itemId
                    }
                    R.id.acction_favorite -> {
                        this@MainActivity.replaceFragment(FavoriteFragment())
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
