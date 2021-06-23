package com.example.myactivity.ui.chatbot

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.ui.menu.MenuActivity
import com.example.myactivity.ui.play.versuscpu.PemainCpuActivity
import com.example.myactivity.ui.play.versusplayer.PemainPemainActivity
import com.example.myactivity.ui.video.VideoActivity
import com.example.myactivity.utils.BotRespon
import com.example.myactivity.utils.KonteksPesan
import com.example.myactivity.utils.KonteksPesan.TERIMA
import kotlinx.coroutines.*

class ChatBotActivity : AppCompatActivity() {
    private lateinit var adapter: ChatBotAdapter
    private lateinit var rvChat: RecyclerView
    private lateinit var etPesanUser: EditText
    private lateinit var btnKirim: Button
    private lateinit var ivBackButton: ImageView
    var listPesan = mutableListOf<ChatBotData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_bot)
        supportActionBar?.hide()
        rvChat = findViewById(R.id.rvChat)
        etPesanUser = findViewById(R.id.etPesanUser)
        btnKirim = findViewById(R.id.btnKirim)
        ivBackButton = findViewById(R.id.ivBackButton)
        ivBackButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        recyclerView()
        buttonKirim()

        pesanUtama("Halo! kenalin ini CaBo, yuk ngobrol seputaran gamenya! Kamu bisa coba keyword dibawah untuk memulai permainan atau informasi cara bermain\n1.Halaman Utama\n2.Main Dengan Teman\n3.Main Dengan Komputer\n4.Cari\n5.Tutorial\n6.TIPS!")

    }


    private fun buttonKirim() {
        btnKirim.setOnClickListener {
            kirimPesan()
        }
        etPesanUser.setOnClickListener {
            GlobalScope.launch {
                delay(200)
                withContext(Dispatchers.Main) {
                    val adapter = ChatBotAdapter()
                    rvChat.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }

    private fun recyclerView() {
        adapter = ChatBotAdapter()
        rvChat.adapter = adapter
        rvChat.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                rvChat.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun kirimPesan() {
        val pesan = etPesanUser.text.toString()
        if (pesan.isNotEmpty()) {
            listPesan.add(ChatBotData(pesan, KonteksPesan.KIRIM))
            etPesanUser.setText("")
            adapter.pesanMasuk(ChatBotData(pesan, KonteksPesan.KIRIM))
            rvChat.scrollToPosition(adapter.itemCount - 1)
            responBot(pesan)
        }
    }

    private fun responBot(pesan: String) {
        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                val responBot = BotRespon.respon(pesan)
                listPesan.add(ChatBotData(responBot, KonteksPesan.TERIMA))
                adapter.pesanMasuk(ChatBotData(responBot, KonteksPesan.TERIMA))
                rvChat.scrollToPosition(adapter.itemCount - 1)
                when (responBot) {
                    KonteksPesan.OPEN_SEARCH -> {
                        val link = Intent(Intent.ACTION_VIEW)
                        val pencarian: String? = pesan.substringAfterLast("Cari")
                        link.data = Uri.parse("https://www.google.com/search?&q=$pencarian")
                        startActivity(link)
                    }
                    KonteksPesan.OPEN_TUTORIAL -> {
                        val link = Intent(Intent.ACTION_VIEW)
                        link.data = Uri.parse("https://www.youtube.com/watch?v=Cl8EwB1DH6k")
                        startActivity(link)
                    }
                    KonteksPesan.MAIN -> {
                        startActivity(Intent(this@ChatBotActivity, MenuActivity::class.java))
                    }
                    KonteksPesan.MAIN_PEMAIN -> {
                        startActivity(
                            Intent(
                                this@ChatBotActivity,
                                PemainPemainActivity::class.java
                            )
                        )
                    }
                    KonteksPesan.MAIN_KOM -> {
                        startActivity(Intent(this@ChatBotActivity, PemainCpuActivity::class.java))
                    }
                    KonteksPesan.TIPS -> {
                        startActivity(Intent(this@ChatBotActivity, VideoActivity::class.java))
                    }
                }
            }
        }
    }

    private fun pesanUtama(pesan: String) {
        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                val adapter = ChatBotAdapter()
                adapter.pesanMasuk(ChatBotData(pesan, TERIMA))
                rvChat.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}