package com.truongdc21.quickquotes

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.truongdc21.quickquotes.data.model.Quotes
import com.truongdc21.quickquotes.ui.activity.ViewPlayActivity
import com.truongdc21.quickquotes.ui.fragment.HomeFragment
import com.truongdc21.quickquotes.ui.fragment.SearchFragment
import com.truongdc21.quickquotes.databinding.ActivityMainBinding
import com.truongdc21.quickquotes.presenter.MainActivity.MainActivityContract
import com.truongdc21.quickquotes.presenter.MainActivity.MainActivityPresenter
import com.truongdc21.quickquotes.ui.fragment.FavoriteViewpagerFragment
import com.truongdc21.quickquotes.utils.*
import com.truongdc21.quickquotes.utils.Notification
import java.util.*

class MainActivity : AppCompatActivity() , MainActivityContract.View {
    
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var mPresenter : MainActivityPresenter? = null
    var itemIDBottomNav : Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setBottomNav()
        createChanelNotification()
        mPresenter = MainActivityPresenter(InitRepository.initRepositoryQuotes(this))
        mPresenter?.setView(this)
        mPresenter?.onStart()
    }

    private fun setBottomNav() {
        binding.apply {
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
                        this@MainActivity.replaceFragmentBottomNAV(FavoriteViewpagerFragment())
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

    private fun scheduleNotification(text : String) {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = resources.getString(R.string.daily_quotes)
        val message = text
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = Constant.HOUR
        calendar[Calendar.MINUTE] = Constant.MINUTE
        var startUpTime = calendar.timeInMillis
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            startUpTime, AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private fun createChanelNotification() {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = Constant.CHANEL_NAME
            val desc = Constant.CHANEL_DES
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance)
            channel.description = desc
            val notificationManager = getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun sendQuotesNotification(quotes: Quotes) {
        scheduleNotification(quotes.mQuotes)
    }
}
