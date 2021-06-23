package com.example.myactivity.ui.chatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.utils.KonteksPesan.KIRIM
import com.example.myactivity.utils.KonteksPesan.TERIMA

class ChatBotAdapter : RecyclerView.Adapter<ChatBotAdapter.PesanViewHolder>(){

    var listPesan = mutableListOf<ChatBotData>()

    inner class PesanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                listPesan.removeAt(bindingAdapterPosition)
                notifyItemRemoved(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesanViewHolder {
        return PesanViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chatbot_item,parent,false))
    }

    override fun onBindViewHolder(holder: PesanViewHolder, position: Int) {
        val posisiPesan = listPesan[position]

        when(posisiPesan.id){
            KIRIM -> {
                holder.itemView.findViewById<TextView>(R.id.tvPesanUser).apply {
                    text = posisiPesan.pesan
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tvPesanBot).visibility = View.GONE
            }
            TERIMA -> {
                holder.itemView.findViewById<TextView>(R.id.tvPesanBot).apply {
                    text = posisiPesan.pesan
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tvPesanUser).visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return listPesan.size
    }

    fun pesanMasuk(chatBotData: ChatBotData){
        this.listPesan.add(chatBotData)
        notifyItemInserted(listPesan.size)
    }
}