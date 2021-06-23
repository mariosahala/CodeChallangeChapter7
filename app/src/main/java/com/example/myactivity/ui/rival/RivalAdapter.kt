package com.example.myactivity.ui.rival

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myactivity.R
import com.example.myactivity.ui.play.versusplayer.PemainPemainActivity
import com.example.myactivity.data.database.Friends

class RivalAdapter(private val list: List<Friends>) :
    RecyclerView.Adapter<RivalAdapter.ViewHolders>() {
    class ViewHolders(view: View) : RecyclerView.ViewHolder(view) {
        private val tvName = view.findViewById<TextView>(R.id.tvName)
        private val tvEmail = view.findViewById<TextView>(R.id.tvEmail)

        fun onBind(friends: Friends) {
            tvName.text = friends.name
            tvEmail.text = friends.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders =
        ViewHolders(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item_friend_layout, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        holder.onBind(list[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, PemainPemainActivity::class.java)
            intent.putExtra("rival", list[position].name)
            it.context.startActivity(intent)
            Toast.makeText(it.context, "Choose, ${list[position].name}", Toast.LENGTH_LONG).show()
            (it.context as RivalActivity).finish()
        }
    }
}