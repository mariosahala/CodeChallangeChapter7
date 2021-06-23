package com.example.myactivity.ui.play.versuscpu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myactivity.R
import com.example.myactivity.databinding.ActivityPemainCpuBinding
import com.example.myactivity.databinding.DialogPermainanSelesaiBinding
import com.example.myactivity.ui.play.ChooseGameActivity
import com.example.myactivity.utils.GamePlayMusic
import com.example.myactivity.utils.SoundPlayer

class PemainCpuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPemainCpuBinding
    private lateinit var viewModel: PemainCpuViewModel
    private var player = ""
    private var loopRandom = 0
    private var scorePlayer = 0
    private var scoreComputer = 0
    private lateinit var sound: SoundPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemainCpuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PemainCpuViewModel::class.java]

        val pilihan = mutableListOf("batu", "gunting", "kertas")
        val pemain =
            mutableListOf(binding.ivBatuPemain, binding.ivGuntingPemain, binding.ivKertasPemain)

        startService(Intent(this, GamePlayMusic::class.java))
        sound = SoundPlayer(this)
        sound.playGameSound()
        binding.tvNamaPemain.text = viewModel.username

        pemain.forEach { it ->
            it.setOnClickListener {
                it.setBackgroundResource(R.drawable.background_click)
                binding.ivDisplayPemain.visibility = View.VISIBLE
                binding.ivDisplayPemain.setImageResource(R.drawable.kertasatas)
                sound.playClickSound()
                player = pilihan[pemain.indexOf(it)]
                Toast.makeText(this, "Pemain memilih $player", Toast.LENGTH_SHORT).show()
                viewModel.activateRandom()
                Log.i("MainGameComputer", "Perulangan Computer #${loopRandom}")
            }
        }

        binding.ivArrowBack.setOnClickListener {
            startActivity(Intent(this, ChooseGameActivity::class.java))
            stopService(Intent(this, GamePlayMusic::class.java))
            finish()
        }

        viewModel.resultRandom.observe(this, Observer {
            randomCpu(it)
            sound.playClickSound()
        })

        viewModel.resultRandomFinish.observe(this, Observer {
            resultCpu(it)
            sound.playClickSound()
        })

        viewModel.resultBattle.observe(this, Observer {
            popUpDialog(it)

        })
    }

    private fun popUpDialog(resultBattle: String) {
        var winner = ""
        when (resultBattle) {
            "Player Win" -> {
                winner = "${viewModel.username}\nWin!"
                scorePlayer++
                binding.tvScorePemain.text = scorePlayer.toString()
            }
            "Computer Win" -> {
                winner = "Computer\nWin!"
                scoreComputer++
                binding.tvScoreCpu.text = scoreComputer.toString()
            }
            "Draw" -> {
                winner = "Draw!"
            }
        }
        Log.e("Result Battle", "Result: $winner")

        val bindingdialog = DialogPermainanSelesaiBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(this@PemainCpuActivity)
        builder.setView(bindingdialog.root)

        val dialog = builder.create()
        dialog.setCancelable(false)
        bindingdialog.tvResult.text = winner
        sound.playGameSound()

        bindingdialog.btnMain.setOnClickListener {
            binding.ivKertasPemain.setBackgroundResource(R.drawable.background_clear)
            binding.ivBatuPemain.setBackgroundResource(R.drawable.background_clear)
            binding.ivGuntingPemain.setBackgroundResource(R.drawable.background_clear)
            binding.ivKertasCpu.setBackgroundResource(R.drawable.background_clear)
            binding.ivGuntingCpu.setBackgroundResource(R.drawable.background_clear)
            binding.ivBatuCpu.setBackgroundResource(R.drawable.background_clear)
            binding.ivDisplayPemain.visibility = View.GONE
            binding.ivDisplayCpu.visibility = View.GONE
            dialog.dismiss()
        }

        bindingdialog.btnKembali.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, ChooseGameActivity::class.java))
            stopService(Intent(this, GamePlayMusic::class.java))
            finish()
        }
        dialog.show()
    }

    private fun randomCpu(resultRandom: String) {
        when (resultRandom) {
            "batu" -> {
                binding.ivBatuCpu.setBackgroundResource(R.drawable.background_click)
                binding.ivDisplayCpu.setImageResource(R.drawable.batubawah)
                Log.i("MainGameComputer", "CPU memilih batu")
            }
            "gunting" -> {
                binding.ivGuntingCpu.setBackgroundResource(R.drawable.background_click)
                binding.ivDisplayCpu.setImageResource(R.drawable.guntingbawah)
                Log.i("MainGameComputer", "CPU memilih gunting")
            }
            "kertas" -> {
                binding.ivKertasCpu.setBackgroundResource(R.drawable.background_click)
                binding.ivDisplayCpu.setImageResource(R.drawable.kertasbawah)
                Log.i("MainGameComputer", "CPU memilih kertas")
            }
        }
        resetBackgroundCpu()
    }

    private fun resetBackgroundCpu() {
        mutableListOf(binding.ivBatuCpu, binding.ivGuntingCpu, binding.ivKertasCpu)
            .forEach { i ->
                Handler(Looper.getMainLooper()).postDelayed({
                    if (i.visibility == View.VISIBLE) {
                        i.setBackgroundResource(R.drawable.background_clear)
                        Log.i("MainGameComputer", "Background Gone")
                    }
                }, 1000L)
            }
    }


    private fun resultCpu(resultCpu: String) {
        when (resultCpu) {
            "batu" -> {
                binding.ivBatuCpu.setBackgroundResource(R.drawable.background_click)
            }
            "gunting" -> {
                binding.ivGuntingCpu.setBackgroundResource(R.drawable.background_click)
            }
            "kertas" -> {
                binding.ivKertasCpu.setBackgroundResource(R.drawable.background_click)
            }
        }
        Toast.makeText(this, "CPU Memilih $resultCpu", Toast.LENGTH_SHORT).show()
        viewModel.battle(player, resultCpu)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, ChooseGameActivity::class.java))
        stopService(Intent(this, GamePlayMusic::class.java))
        finish()
    }
}