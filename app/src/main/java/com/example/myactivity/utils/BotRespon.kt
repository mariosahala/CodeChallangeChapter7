package com.example.myactivity.utils

import com.example.myactivity.utils.KonteksPesan.MAIN
import com.example.myactivity.utils.KonteksPesan.MAIN_KOM
import com.example.myactivity.utils.KonteksPesan.MAIN_PEMAIN
import com.example.myactivity.utils.KonteksPesan.OPEN_SEARCH
import com.example.myactivity.utils.KonteksPesan.OPEN_TUTORIAL
import com.example.myactivity.utils.KonteksPesan.TIPS

object BotRespon {
    fun respon(pesanRespon: String): String {
        val pesan = pesanRespon.toLowerCase()
        val random = (0..2).random()


        return when {
            pesan.contains("halo") -> " halo juga"
            pesan.contains("kabar") -> "baik dong"
            pesan.contains("info") -> "Kamu bisa main batu gunting kertas dengan komputer atau bisa ajak temen kamu buat main bareng"
            pesan.contains("halaman utama") -> {
                MAIN
            }
            pesan.contains("main dengan teman") -> {
                MAIN_PEMAIN
            }
            pesan.contains("main dengan komputer")->{
                MAIN_KOM
            }

            pesan.contains("cari") -> {
                OPEN_SEARCH
            }
            pesan.contains("tutorial") -> {
                OPEN_TUTORIAL
            }
            pesan.contains("tips") -> {
                TIPS
            }
            else -> {
                when (random) {
                    0 -> "CaBo nggak paham maksudnya"
                    1 -> "masih belum paham, CaBo bisa bantuin kamu nonton tutorial main batu gunting kertas lho!, coba deh ketik tutorial"
                    2 -> "mau CaBo cariin pertanyaannya di google? kamu bisa tambah keyword CARI sebelum pertanyaan kamu"
                    else -> "mungkin bisa coba tanya yang lain"
                }
            }
        }
    }

}