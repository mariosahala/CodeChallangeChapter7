package com.example.myactivity.ui.play.versusplayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.R
import com.example.myactivity.databinding.ActivityPemainPemainBinding
import com.example.myactivity.databinding.DialogPermainanSelesaiBinding
import com.example.myactivity.ui.play.ChooseGameActivity
import com.example.myactivity.utils.GamePlayMusic
import com.example.myactivity.utils.SoundPlayer

class PemainPemainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPemainPemainBinding
    private lateinit var viewModel: PemainPemainViewModel
    private lateinit var sound: SoundPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemainPemainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        startService(Intent(this, GamePlayMusic::class.java))

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(application)
        )[PemainPemainViewModel::class.java]

        val rivalName = intent.getStringExtra("rival").toString()
        viewModel.rival = rivalName
        binding.tvPemain2.text = rivalName
        binding.tvPemain1.text = viewModel.pemain

        val pemain1 = mutableListOf(
            binding.ivBatuPemain,
            binding.ivGuntingPemain,
            binding.ivKertasPemain
        )

        val pemain2 = mutableListOf(
            binding.ivBatuPemain2,
            binding.ivGuntingPemain2,
            binding.ivKertasPemain2
        )

        val displayPemain = mutableListOf(
            binding.ivDisplayPemain,
            binding.ivDisplayPemain2
        )

        val pilihan = mutableListOf("batu", "gunting", "kertas")
        var score = 0
        var scoreRival = 0

        sound = SoundPlayer(this)
        sound.playGameSound()

        displayPemain.forEach {
            it.visibility = View.GONE
        }

        pemain1.forEach { it ->
            it.setOnClickListener {
                it.setBackgroundResource(R.drawable.background_click)
                viewModel.pilihan = pilihan[pemain1.indexOf(it)]
                sound.playClickSound()

                pemain1.forEach {
                    it.visibility = View.GONE
                }

                pemain2.forEach {
                    it.isClickable = true
                    it.visibility = View.VISIBLE
                }
            }
        }

        pemain2.forEach { it ->
            it.setOnClickListener {
                it.setBackgroundResource(R.drawable.background_click)
                viewModel.pilihanLawan = pilihan[pemain2.indexOf(it)]
                viewModel.play()
                sound.playClickSound()

                pemain1.forEach { it2 ->
                    it2.visibility = View.VISIBLE
                }

                displayPemain.forEach { it3 ->
                    it3.visibility = View.VISIBLE
                }
            }
        }

        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, ChooseGameActivity::class.java))
            stopService(Intent(this, GamePlayMusic::class.java))
            finish()
        }

        viewModel.result().observe(this, Observer { result ->
            viewModel.simpanBattle()
            val _binding = DialogPermainanSelesaiBinding.inflate(layoutInflater)
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(_binding.root)
            val dialog = dialogBuilder.create()
            dialog.setCancelable(false)
            dialog.show()
            _binding.tvResult.text = result
            sound.playGameSound()

            _binding.btnMain.setOnClickListener {
                pemain1.forEach {
                    it.setBackgroundResource(R.drawable.background_clear)
                }

                pemain2.forEach {
                    it.visibility = View.GONE
                    it.setBackgroundResource(R.drawable.background_clear)
                }

                displayPemain.forEach { it.visibility = View.GONE }
                dialog.dismiss()
            }

            _binding.btnKembali.setOnClickListener {
                dialog.dismiss()
                startActivity(Intent(this, ChooseGameActivity::class.java))
                stopService(Intent(this, GamePlayMusic::class.java))
                finish()
            }

            when (viewModel.skor) {
                1 -> {
                    score++
                    binding.tvScore.text = "Score: $score"
                }
                2 -> {
                    scoreRival++
                    binding.tvScore2.text = "Score: $scoreRival"
                }
            }

            when (viewModel.pilihan) {
                "batu" -> binding.ivDisplayPemain.setImageResource(R.drawable.batuatas)
                "gunting" -> binding.ivDisplayPemain.setImageResource(R.drawable.guntingatas)
                "kertas" -> binding.ivDisplayPemain.setImageResource(R.drawable.kertasatas)

            }

            when (viewModel.pilihanLawan) {
                "batu" -> binding.ivDisplayPemain2.setImageResource(R.drawable.batubawah)
                "gunting" -> binding.ivDisplayPemain2.setImageResource(R.drawable.guntingbawah)
                "kertas" -> binding.ivDisplayPemain2.setImageResource(R.drawable.kertasbawah)
            }
        })
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ChooseGameActivity::class.java))
        stopService(Intent(this, GamePlayMusic::class.java))
        finish()
    }
}
