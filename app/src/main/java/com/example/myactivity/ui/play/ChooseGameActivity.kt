package com.example.myactivity.ui.play

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myactivity.R
import com.example.myactivity.ui.menu.MenuActivity
import com.example.myactivity.ui.play.versuscpu.PemainCpuActivity
import com.example.myactivity.ui.play.versusplayer.PemainPemainActivity
import com.example.myactivity.ui.rival.RivalActivity

class ChooseGameActivity : AppCompatActivity() {
    private lateinit var ivVersusPlayer: ImageView
    private lateinit var ivVersusCom: ImageView
    private lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_game)
        ivVersusPlayer = findViewById(R.id.ivVersusPlayer)
        ivVersusCom = findViewById(R.id.ivVersusCom)
        ivBack = findViewById(R.id.ivBackMenu)

        ivVersusPlayer.setOnClickListener {
            startActivity(Intent(this, RivalActivity::class.java))
            finish()
        }

        ivVersusCom.setOnClickListener {
            startActivity(Intent(this, PemainCpuActivity::class.java))
            finish()
        }

        ivBack.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }
    }
}