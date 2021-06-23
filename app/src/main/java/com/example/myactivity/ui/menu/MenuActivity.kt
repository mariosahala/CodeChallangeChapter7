package com.example.myactivity.ui.menu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.R
import com.example.myactivity.ui.Friends.FriendsActivity
import com.example.myactivity.ui.chatbot.ChatBotActivity
import com.example.myactivity.ui.history.HistoryActivity
import com.example.myactivity.ui.play.ChooseGameActivity
import com.example.myactivity.ui.profile.ProfileActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.system.exitProcess

class MenuActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var btnPlay: Button
    private lateinit var btnFriends: Button
    private lateinit var btnHistory: Button
    private lateinit var btnProfile: Button
    private lateinit var btnExit: Button
    private lateinit var fabChat: FloatingActionButton
    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar?.hide()

        tvName = findViewById(R.id.tvName)
        btnPlay = findViewById(R.id.btnPlay)
        btnFriends = findViewById(R.id.btnFriends)
        btnHistory = findViewById(R.id.btnHistory)
        btnProfile = findViewById(R.id.btnProfile)
        btnExit = findViewById(R.id.btnExit)
        fabChat = findViewById(R.id.fabChat)
        viewModel = ViewModelProvider(this)[MenuViewModel::class.java]

        tvName.text = "Hi, ${viewModel.name}!"

        btnPlay.setOnClickListener {
            startActivity(Intent(this, ChooseGameActivity::class.java))
        }

        btnFriends.setOnClickListener {
            startActivity(Intent(this, FriendsActivity::class.java))
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        fabChat.setOnClickListener {
            startActivity(Intent(this, ChatBotActivity::class.java))
        }

        btnExit.setOnClickListener {
            moveTaskToBack(true)
            exitProcess(-1)
        }

    }
}