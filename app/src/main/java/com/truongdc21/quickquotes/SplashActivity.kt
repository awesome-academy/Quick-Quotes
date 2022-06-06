package com.truongdc21.quickquotes

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import com.truongdc21.quickquotes.Utils.Constance
import com.truongdc21.quickquotes.Utils.ToolsUI.setStatusBar
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {
    private val job = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setStatusBar(R.color.splash_bgr)
        setContentView(R.layout.activity_splash)
        CoroutineScope(job + Dispatchers.Main).launch {
            delay(1500)
            startActivity(Intent(this@SplashActivity , MainActivity::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
