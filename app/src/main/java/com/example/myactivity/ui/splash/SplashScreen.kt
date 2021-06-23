@file:Suppress("DEPRECATION")

package com.example.myactivity.ui.splash

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.R
import com.example.myactivity.ui.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME: Long = 3000
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mediaPlayer = MediaPlayer.create(this, R.raw.intro)
        mediaPlayer.start()
        Log.d("SPLASH SCREEN", "SPLASH SCREEN")
        mediaPlayer.setOnCompletionListener {
            Intent(this, LoginActivity::class.java)
            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                mediaPlayer.stop()
            }, SPLASH_TIME)
        }
    }
}