package com.example.myactivity.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.data.model.battle.GetBattleHistory

class HistoryAdapter(val list: List<GetBattleHistory.Data>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolders>() {
    class ViewHolders(v: View) : RecyclerView.ViewHolder(v) {
        val tvPlay: TextView = v.findViewById(R.id.tvPlay)
        val tvResult: TextView = v.findViewById(R.id.tvResultGame)
        val tvDate: TextView = v.findViewById(R.id.tvDate)

        fun onBind(data: GetBattleHistory.Data) {
            tvPlay.text = data.mode
            tvResult.text = data.message
            tvDate.text = data.updatedAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders =
        ViewHolders(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.onBind(list[position])
    }

}